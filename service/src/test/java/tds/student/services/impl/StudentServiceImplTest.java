package tds.student.services.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

import tds.student.Student;
import tds.student.repositories.StudentRepository;
import tds.student.services.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentServiceImplTest {
    private StudentRepository repository;
    private StudentService studentService;

    @Before
    public void setUp() {
        repository = mock(StudentRepository.class);
        studentService = new StudentServiceImpl(repository);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void shouldReturnStudent() {
        Student student = new Student(1, "testId", "CA", "clientName");
        when(repository.findStudentById(1)).thenReturn(Optional.of(student));

        Optional<Student> optionalStudent = studentService.findStudentById(1);

        verify(repository).findStudentById(1);

        assertThat(optionalStudent.isPresent()).isTrue();
        assertThat(optionalStudent.get().getId()).isEqualTo(1);
    }
}
