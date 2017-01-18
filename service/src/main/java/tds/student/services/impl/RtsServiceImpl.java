package tds.student.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import tds.common.cache.CacheType;
import tds.dll.common.rtspackage.IRtsPackageReader;
import tds.dll.common.rtspackage.common.exception.RtsPackageReaderException;
import tds.student.RtsStudentPackageAttribute;
import tds.student.repositories.RtsStudentPackageQueryRepository;
import tds.student.services.RtsService;

@Service
class RtsServiceImpl implements RtsService {
    private final RtsStudentPackageQueryRepository rtsStudentPackageQueryRepository;
    private final IRtsPackageReader packageReader;

    @Autowired
    public RtsServiceImpl(RtsStudentPackageQueryRepository rtsStudentPackageQueryRepository, IRtsPackageReader packageReader) {
        this.rtsStudentPackageQueryRepository = rtsStudentPackageQueryRepository;
        this.packageReader = packageReader;
    }

    @Override
    @Cacheable(CacheType.MEDIUM_TERM)
    public List<RtsStudentPackageAttribute> findRtsStudentPackageAttributes(String clientName, long studentId, String[] attributeNames) {
        Optional<byte[]> maybePackage = rtsStudentPackageQueryRepository.findRtsStudentPackage(clientName, studentId);
        List<RtsStudentPackageAttribute> attributes = new ArrayList<>();
        if (!maybePackage.isPresent()) {
            return attributes;
        }

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
}
