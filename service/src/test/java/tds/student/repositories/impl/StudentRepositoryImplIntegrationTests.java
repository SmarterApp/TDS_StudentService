/***************************************************************************************************
 * Copyright 2017 Regents of the University of California. Licensed under the Educational
 * Community License, Version 2.0 (the “license”); you may not use this file except in
 * compliance with the License. You may obtain a copy of the license at
 *
 * https://opensource.org/licenses/ECL-2.0
 *
 * Unless required under applicable law or agreed to in writing, software distributed under the
 * License is distributed in an “AS IS” BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for specific language governing permissions
 * and limitations under the license.
 **************************************************************************************************/

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
