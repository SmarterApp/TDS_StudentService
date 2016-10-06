package tds.student.services;

import java.util.Optional;

import tds.student.RtsAttribute;

/**
 * Handles interactions with session rts packages
 */
public interface RtsService {
    /**
     * Retrieves the student package and finds the attribute
     *
     * @param clientName    the client name
     * @param studentId     the student id associated with the package
     * @param attributeName the attribute name to be used for lookup within the package
     * @return the {@link tds.student.RtsAttribute} otherwise empty
     */
    Optional<RtsAttribute> findRtsStudentPackageAttribute(String clientName, long studentId, String attributeName);
}
