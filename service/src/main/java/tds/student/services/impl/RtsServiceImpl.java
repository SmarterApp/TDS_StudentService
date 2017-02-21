package tds.student.services.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.xml.datatype.XMLGregorianCalendar;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tds.common.cache.CacheType;
import tds.dll.common.rtspackage.IRtsPackageReader;
import tds.dll.common.rtspackage.common.exception.RtsPackageReaderException;
import tds.dll.common.rtspackage.common.table.RtsRecord;
import tds.dll.common.rtspackage.student.StudentPackageReader;
import tds.dll.common.rtspackage.student.data.StudentPackage;
import tds.student.RtsStudentPackageAttribute;
import tds.student.RtsStudentPackageRelationship;
import tds.student.Student;
import tds.student.model.RtsStudentInfo;
import tds.student.repositories.RtsStudentPackageQueryRepository;
import tds.student.services.RtsService;

@Service
class RtsServiceImpl implements RtsService {
    private static final String DEFAULT_DATE_FORMAT = "yyyy-MM-dd";

    private final RtsStudentPackageQueryRepository rtsStudentPackageQueryRepository;


    @Autowired
    public RtsServiceImpl(final RtsStudentPackageQueryRepository rtsStudentPackageQueryRepository) {
        this.rtsStudentPackageQueryRepository = rtsStudentPackageQueryRepository;
    }

    @Override
    @Cacheable(CacheType.MEDIUM_TERM)
    public List<RtsStudentPackageAttribute> findRtsStudentPackageAttributes(final String clientName, final long studentId, final String[] attributeNames) {
        Optional<byte[]> maybePackage = rtsStudentPackageQueryRepository.findRtsStudentPackage(clientName, studentId);
        List<RtsStudentPackageAttribute> attributes = new ArrayList<>();
        if (!maybePackage.isPresent()) {
            return attributes;
        }

        IRtsPackageReader packageReader = new StudentPackageReader();
        try {
            if (packageReader.read(maybePackage.get())) {
                for (String attributeName : attributeNames) {
                    String value = packageReader.getFieldValue(attributeName);
                    if (value != null) {
                        attributes.add(new RtsStudentPackageAttribute(attributeName, value));
                    }
                }
            }
        } catch (RtsPackageReaderException e) {
            throw new RuntimeException(e);
        }

        return attributes;
    }

    @Override
    @Cacheable(CacheType.MEDIUM_TERM)
    public Optional<Student> findStudent(final String clientName, final long studentId) {
        Optional<RtsStudentInfo> maybeStudentInfo = rtsStudentPackageQueryRepository.findStudentInfo(clientName, studentId);
        if (!maybeStudentInfo.isPresent()) {
            return Optional.empty();
        }

        RtsStudentInfo studentInfo = maybeStudentInfo.get();
        IRtsPackageReader packageReader = new StudentPackageReader();
        try {
            if (packageReader.read(studentInfo.getStudentPackage())) {
                StudentPackage studentPackage = packageReader.getPackage(StudentPackage.class);
                tds.dll.common.rtspackage.student.data.Student rtsStudent = studentPackage.getStudent();
                List<RtsStudentPackageAttribute> attributes = parseAttributesOutOfPackage(packageReader, rtsStudent);
                List<RtsStudentPackageRelationship> relationships = parseRelationshipsOutOfPackage(packageReader);

                Student student = new Student.Builder(studentInfo.getId(), studentInfo.getClientName())
                    .withLoginSSID(studentInfo.getLoginSSID())
                    .withStateCode(studentInfo.getStateCode())
                    .withStudentPackage(rtsStudent)
                    .withAttributes(attributes)
                    .withRelationships(relationships)
                    .build();

                return Optional.of(student);
            }
        } catch (RtsPackageReaderException e) {
            throw new RuntimeException(e);
        }

        return Optional.empty();
    }

    private static List<RtsStudentPackageAttribute> parseAttributesOutOfPackage(final IRtsPackageReader studentReader,
                                                                                final tds.dll.common.rtspackage.student.data.Student student) {
        List<RtsStudentPackageAttribute> attributes = new ArrayList<>();

        attributes.add(new RtsStudentPackageAttribute("LastName", studentReader.getFieldValue("LglLNm")));
        attributes.add(new RtsStudentPackageAttribute("FirstName", studentReader.getFieldValue("LglFNm")));
        attributes.add(new RtsStudentPackageAttribute("MiddleName", student.getMiddleName()));
        attributes.add(new RtsStudentPackageAttribute("SSID", student.getStudentIdentifier()));
        attributes.add(new RtsStudentPackageAttribute("DOB", studentReader.getFieldValue("BirthDtTxt")));
        attributes.add(new RtsStudentPackageAttribute("Gender", studentReader.getFieldValue("Gndr")));
        attributes.add(new RtsStudentPackageAttribute("HispanicOrLatinoEthnicity", student.getHispanicOrLatinoEthnicity()));
        attributes.add(new RtsStudentPackageAttribute("AmericanIndianOrAlaskaNative", student.getAmericanIndianOrAlaskaNative()));
        attributes.add(new RtsStudentPackageAttribute("Asian", student.getAsian()));
        attributes.add(new RtsStudentPackageAttribute("BlackOrAfricanAmerican", student.getBlackOrAfricanAmerican()));
        attributes.add(new RtsStudentPackageAttribute("White", student.getWhite()));
        attributes.add(new RtsStudentPackageAttribute("NativeHawaiianOrOtherPacificIslander", student.getNativeHawaiianOrOtherPacificIslander()));
        attributes.add(new RtsStudentPackageAttribute("DemographicRaceTwoOrMoreRaces", student.getDemographicRaceTwoOrMoreRaces()));
        attributes.add(new RtsStudentPackageAttribute("IDEAIndicator", student.getIDEAIndicator()));
        attributes.add(new RtsStudentPackageAttribute("LEPStatus", student.getLEPStatus()));
        attributes.add(new RtsStudentPackageAttribute("Section504Status", student.getSection504Status()));

        attributes.add(new RtsStudentPackageAttribute("EconomicDisadvantageStatus", student.getEconomicDisadvantageStatus()));
        // SB-512
        attributes.add(new RtsStudentPackageAttribute("GradeLevelWhenAssessed", student.getGradeLevelWhenAssessed()));

        attributes.add(new RtsStudentPackageAttribute("LEPExitDate",
            formatDateString(student.getLEPExitDate())));

        attributes.add(new RtsStudentPackageAttribute("AlternateSSID", student.getAlternateSSID()));
        attributes.add(new RtsStudentPackageAttribute("MigrantStatus", StringUtils.defaultString(student.getMigrantStatus())));
        attributes.add(new RtsStudentPackageAttribute("LanguageCode", StringUtils.defaultString(student.getLanguageCode())));

        attributes.add(new RtsStudentPackageAttribute("EnglishLanguageProficiencLevel",
            StringUtils.defaultString(student.getEnglishLanguageProficiencyLevel())));

        attributes.add(new RtsStudentPackageAttribute("FirstEntryDateIntoUSSchool",
            formatDateString(student.getFirstEntryDateIntoUSSchool())));

        attributes.add(new RtsStudentPackageAttribute("LimitedEnglishProficiencyEntryDate",
            formatDateString(student.getLimitedEnglishProficiencyEntryDate())));

        attributes.add(new RtsStudentPackageAttribute("TitleIIILanguageInstructionProgramType",
            StringUtils.defaultString(student.getTitleIIILanguageInstructionProgramType())));

        attributes.add(new RtsStudentPackageAttribute("PrimaryDisabilityType",
            StringUtils.defaultString(student.getPrimaryDisabilityType())));

        return attributes;
    }

    private static List<RtsStudentPackageRelationship> parseRelationshipsOutOfPackage(final IRtsPackageReader packageReader) {
        //Replaces RtsPackageDLL._GetTesteeRelationships_SP
        List<RtsStudentPackageRelationship> relationships = new ArrayList<>();

        RtsRecord rtsRecord = packageReader.getRtsRecord("ENRDIST-STUDENT");
        if (rtsRecord != null) {
            RtsStudentPackageRelationship district = new RtsStudentPackageRelationship(
                "DistrictID",
                "District",
                rtsRecord.get("entityId"),
                rtsRecord.get("entityKey")
            );

            RtsStudentPackageRelationship districtName = new RtsStudentPackageRelationship(
                "DistrictName",
                "District",
                rtsRecord.get("entityName"),
                rtsRecord.get("entityKey")
            );

            relationships.add(district);
            relationships.add(districtName);
        }
        rtsRecord = packageReader.getRtsRecord("ENRLINST-STUDENT");
        if (rtsRecord != null) {

            RtsStudentPackageRelationship schoolId = new RtsStudentPackageRelationship(
                "SchoolID",
                "School",
                rtsRecord.get("entityId"),
                rtsRecord.get("entityKey")
            );

            RtsStudentPackageRelationship school = new RtsStudentPackageRelationship(
                "SchoolName",
                "School",
                rtsRecord.get("entityName"),
                rtsRecord.get("entityKey")
            );

            relationships.add(school);
            relationships.add(schoolId);
        }
        rtsRecord = packageReader.getRtsRecord("STATE-STUDENT");
        if (rtsRecord != null) {
            RtsStudentPackageRelationship state = new RtsStudentPackageRelationship(
                "StateAbbreviation",
                "State",
                rtsRecord.get("entityName"),
                rtsRecord.get("entityKey")
            );

            RtsStudentPackageRelationship stateName = new RtsStudentPackageRelationship(
                "StateName",
                "State",
                rtsRecord.get("entityName"),
                rtsRecord.get("entityKey")
            );

            relationships.add(state);
            relationships.add(stateName);
        }

        return relationships;
    }

    private static String formatDateString(final XMLGregorianCalendar date) {
        if(date == null) return "";

        SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
        return sdf.format(date.toGregorianCalendar().getTime());
    }
}
