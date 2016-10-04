package tds.student.services;

import java.util.Optional;

import tds.student.RtsAttribute;

/**
 * Handles interactions with session rts packages
 */
public interface RtsService {
    Optional<RtsAttribute> findRtsStudentPackage(String clientName, long studentId, String attributeName);
}
