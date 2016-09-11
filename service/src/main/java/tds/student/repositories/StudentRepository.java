package tds.student.repositories;

import java.util.Optional;

import tds.student.Student;

/**
 * Handles data access for {@link tds.student.Student Student}
 */
public interface StudentRepository {
    /**
     * Get {@link tds.student.Student Student} by id
     *
     * @param id unique id for the student
     * @return {@link tds.student.Student Student} if found otherwise empty
     */
    Optional<Student> getStudentById(long id);
}
