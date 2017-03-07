package tds.student.repositories.impl;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import tds.student.Student;
import tds.student.repositories.StudentRepository;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class StudentRepositoryImplIntegrationTests {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Before
    public void setUp() {
        String studentKeySQL = "insert into r_studentkeyid values (1, 'adv001', 'CA', 'SBAC_PT')";
        jdbcTemplate.execute(studentKeySQL);
    }

    @After
    public void tearDown() {
    }

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
