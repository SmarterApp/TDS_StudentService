package tds.student.repositories;

import java.util.Optional;

/**
 * Handles the data access interactions with the Student Package (r_studentpackage table).
 */
public interface RtsStudentPackageQueryRepository {
    /**
     * Finds the Student package
     *
     * @param clientName client name for the student
     * @param studentId  student id for the package
     * @return byte array containing the serialized gzipped data if found otherwise empty
     */
    Optional<byte[]> findRtsStudentPackage(String clientName, long studentId);
}
