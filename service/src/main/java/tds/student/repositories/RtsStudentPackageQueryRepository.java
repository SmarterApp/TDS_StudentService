package tds.student.repositories;

import java.util.Optional;

public interface RtsStudentPackageQueryRepository {
    Optional<byte[]> findRtsStudentPackage(String clientName, long studentId);
}
