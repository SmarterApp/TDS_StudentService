package tds.student.repositories;

import java.util.Optional;

import tds.student.model.RtsStudentInfo;

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
    Optional<byte[]> findRtsStudentPackage(final String clientName, final long studentId);

    /**
     * Finds the {@link tds.student.model.RtsStudentInfo} for the client name and id
     *
     * @param clientName client name for the student
     * @param studentId  the student id
     * @return {@link tds.student.model.RtsStudentInfo}
     */
    Optional<RtsStudentInfo> findStudentInfo(final String clientName, final long studentId);
}
