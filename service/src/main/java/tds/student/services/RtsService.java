package tds.student.services;

import java.util.List;

import tds.student.RtsStudentPackageAttribute;

/**
 * Handles interactions with session rts packages
 */
public interface RtsService {
    /**
     * Retrieves the student package and finds the attribute
     *
     * @param clientName     the client name
     * @param studentId      the student id associated with the package
     * @param attributeNames the attribute names to be used for lookup within the package
     * @return list of {@link tds.student.RtsStudentPackageAttribute} otherwise empty
     */
    List<RtsStudentPackageAttribute> findRtsStudentPackageAttributes(String clientName, long studentId, String[] attributeNames);
}
