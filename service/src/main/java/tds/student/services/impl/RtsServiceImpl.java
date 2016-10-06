package tds.student.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import tds.dll.common.rtspackage.IRtsPackageReader;
import tds.dll.common.rtspackage.common.exception.RtsPackageReaderException;
import tds.student.RtsAttribute;
import tds.student.repositories.RtsStudentPackageQueryRepository;
import tds.student.services.RtsService;

@Service
public class RtsServiceImpl implements RtsService {
    private final RtsStudentPackageQueryRepository rtsStudentPackageQueryRepository;
    private final IRtsPackageReader packageReader;

    @Autowired
    public RtsServiceImpl(RtsStudentPackageQueryRepository rtsStudentPackageQueryRepository, IRtsPackageReader packageReader) {
        this.rtsStudentPackageQueryRepository = rtsStudentPackageQueryRepository;
        this.packageReader = packageReader;
    }

    @Override
    public Optional<RtsAttribute> findRtsStudentPackageAttribute(String clientName, long studentId, String attributeName) {
        Optional<byte[]> maybePackage = rtsStudentPackageQueryRepository.findRtsStudentPackage(clientName, studentId);

        Optional<RtsAttribute> maybeAttribute = Optional.empty();

        if (maybePackage.isPresent()) {
            try {
                if (packageReader.read(maybePackage.get())) {
                    String value = packageReader.getFieldValue(attributeName);
                    if(value != null) {
                        maybeAttribute = Optional.of(new RtsAttribute(attributeName, value));
                    }
                }
            } catch (RtsPackageReaderException e) {
                throw new RuntimeException(e);
            }
        }

        return maybeAttribute;
    }
}
