package tds.student.repositories.impl;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import tds.student.Student;
import tds.student.repositories.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StudentRepositoryImplIntegrationTests {
    @Autowired
    private StudentRepository studentRepository;

    @Test
    public void shouldRetrieveStudentForId() {
        Optional<Student> maybeStudent = studentRepository.findStudentById(1);
        assertThat(maybeStudent.isPresent()).isTrue();
        Student student = maybeStudent.get();
        assertThat(student.getId()).isEqualTo(1);
        assertThat(student.getClientName()).isEqualTo("SBAC_PT");
        assertThat(student.getLoginSSID()).isEqualTo("adv001");
        assertThat(student.getStateCode()).isEqualTo("CA");
    }

    @Test
    public void shouldHandleRetrieveStudentIdNotFound() {
        Optional<Student> studentOptional = studentRepository.findStudentById(99);
        assertThat(studentOptional.isPresent()).isFalse();
    }
}
