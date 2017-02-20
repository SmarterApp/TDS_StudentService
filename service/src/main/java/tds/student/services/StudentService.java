package tds.student.services;

import java.util.Optional;

import tds.student.Student;

/**
 * Service handling student related actions
 */
public interface StudentService {
    /**
     * Retrieves the {@link tds.student.Student Student} by id
     *
     * @param id student key
     * @return student if found otherwise empty optional
     * @deprecated Please use {@link tds.student.services.RtsService} findStudent instead
     */
    @Deprecated
    Optional<Student> findStudentById(final long id);
}
