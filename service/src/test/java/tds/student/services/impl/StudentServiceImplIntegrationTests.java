package tds.student.services.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Optional;

import tds.student.Student;
import tds.student.repositories.StudentRepository;
import tds.student.services.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class StudentServiceImplIntegrationTests {
    @MockBean
    private StudentRepository repository;

    @Autowired
    private StudentService studentService;

    @Test
    public void shouldReturnCachedStudent() {
        Student student = new Student(1, "testId", "CA", "clientName");
        when(repository.findStudentById(1)).thenReturn(Optional.of(student));

        Optional<Student> optionalStudent1 = studentService.findStudentById(1);
        Optional<Student> optionalStudent2 = studentService.findStudentById(1);

        verify(repository, times(1)).findStudentById(1);

        assertThat(optionalStudent1.isPresent()).isTrue();
        assertThat(optionalStudent1).isEqualTo(optionalStudent2);
    }
}
