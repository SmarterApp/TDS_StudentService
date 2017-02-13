package tds.student.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

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
    private final RtsStudentPackageQueryRepository rtsStudentPackageQueryRepository;

    @Autowired
    public RtsServiceImpl(RtsStudentPackageQueryRepository rtsStudentPackageQueryRepository) {
        this.rtsStudentPackageQueryRepository = rtsStudentPackageQueryRepository;
    }

    @Override
    @Cacheable(CacheType.MEDIUM_TERM)
    public List<RtsStudentPackageAttribute> findRtsStudentPackageAttributes(String clientName, long studentId, String[] attributeNames) {
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
    public Optional<Student> findStudent(String clientName, long studentId) {
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

    private static List<RtsStudentPackageAttribute> parseAttributesOutOfPackage(IRtsPackageReader studentReader, tds.dll.common.rtspackage.student.data.Student student) {
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
        if (student.getLEPExitDate() == null) {
            attributes.add(new RtsStudentPackageAttribute("LEPExitDate", ""));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            attributes.add(new RtsStudentPackageAttribute("LEPExitDate", sdf.format(student.getLEPExitDate())));
        }

        attributes.add(new RtsStudentPackageAttribute("AlternateSSID", student.getAlternateSSID()));

        if (student.getMigrantStatus() == null) {
            attributes.add(new RtsStudentPackageAttribute("MigrantStatus", ""));
        } else {
            attributes.add(new RtsStudentPackageAttribute("MigrantStatus", student.getMigrantStatus()));
        }

        if (student.getLanguageCode() == null) {
            attributes.add(new RtsStudentPackageAttribute("LanguageCode", ""));
        } else {
            attributes.add(new RtsStudentPackageAttribute("LanguageCode", student.getLanguageCode()));
        }

        if (student.getEnglishLanguageProficiencyLevel() == null) {
            attributes.add(new RtsStudentPackageAttribute("EnglishLanguageProficiencLevel", ""));
        } else {
            attributes.add(new RtsStudentPackageAttribute("EnglishLanguageProficiencLevel",
                student.getEnglishLanguageProficiencyLevel()));
        }

        if (student.getFirstEntryDateIntoUSSchool() == null) {
            attributes.add(new RtsStudentPackageAttribute("FirstEntryDateIntoUSSchool", ""));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            attributes.add(new RtsStudentPackageAttribute("FirstEntryDateIntoUSSchool",
                sdf.format(student.getFirstEntryDateIntoUSSchool())));
        }

        if (student.getLimitedEnglishProficiencyEntryDate() == null) {
            attributes.add(new RtsStudentPackageAttribute("LimitedEnglishProficiencyEntryDate", ""));
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            attributes.add(new RtsStudentPackageAttribute("LimitedEnglishProficiencyEntryDate",
                sdf.format(student.getLimitedEnglishProficiencyEntryDate())));
        }

        if (student.getTitleIIILanguageInstructionProgramType() == null) {
            attributes.add(new RtsStudentPackageAttribute("TitleIIILanguageInstructionProgramType", ""));
        } else {
            attributes.add(new RtsStudentPackageAttribute("TitleIIILanguageInstructionProgramType",
                student.getTitleIIILanguageInstructionProgramType()));
        }

        if (student.getPrimaryDisabilityType() == null) {
            attributes.add(new RtsStudentPackageAttribute("PrimaryDisabilityType", ""));
        } else {
            attributes.add(new RtsStudentPackageAttribute("PrimaryDisabilityType",
                student.getPrimaryDisabilityType()));
        }

        return attributes;
    }

    private static List<RtsStudentPackageRelationship> parseRelationshipsOutOfPackage(IRtsPackageReader packageReader) {
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
                rtsRecord.get("entintyName"),
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
}
