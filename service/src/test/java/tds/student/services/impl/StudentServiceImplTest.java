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
        Student student = new Student.Builder(1, "clientName")
            .withStateCode("CA")
            .withLoginSSID("testId")
            .build();
        when(repository.findStudentById(1)).thenReturn(Optional.of(student));

        Optional<Student> optionalStudent = studentService.findStudentById(1);

        verify(repository).findStudentById(1);

        assertThat(optionalStudent.isPresent()).isTrue();
        assertThat(optionalStudent.get().getId()).isEqualTo(1);
    }
}
