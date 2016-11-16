package tds.student.web.endpoints;


import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Collections;
import java.util.Optional;

import tds.common.web.advice.ExceptionAdvice;
import tds.student.RtsStudentPackageAttribute;
import tds.student.Student;
import tds.student.services.RtsService;
import tds.student.services.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
@Import(ExceptionAdvice.class)
public class StudentControllerIntegrationTests {
    @Autowired
    private MockMvc http;

    @MockBean
    private StudentService studentService;

    @MockBean
    private RtsService rtsService;

    @Test
    public void shouldReturnStudent() throws Exception {
        Student student = new Student(1, "studentId", "CA", "client");
        when(studentService.findStudentById(1)).thenReturn(Optional.of(student));

        http.perform(get(new URI("/students/1"))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("id", is(1)))
            .andExpect(jsonPath("loginSSID", is("studentId")))
            .andExpect(jsonPath("stateCode", is("CA")))
            .andExpect(jsonPath("clientName", is("client")));

        verify(studentService).findStudentById(1);
    }

    @Test
    public void shouldReturnNotFoundWhenStudentNotFound() throws Exception {
        when(studentService.findStudentById(1)).thenReturn(Optional.empty());
        http.perform(get(new URI("/students/1"))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(studentService).findStudentById(1);
    }

    @Test
    @Ignore("Spring MVC Integration tests does not currently handle Matrix Variables")
    public void shouldReturnRtsAttributeForStudent() throws Exception {
        ArgumentCaptor<String[]> captor = ArgumentCaptor.forClass(String[].class);
        when(rtsService.findRtsStudentPackageAttributes(any(String.class), any(int.class), any(String[].class)))
            .thenReturn(Collections.singletonList(new RtsStudentPackageAttribute("testName", "testValue")));

        http.perform(get(new URI("/students/1/rts/client/attributes=testName,otherName"))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("[0].name", is("testName")))
            .andExpect(jsonPath("[0].value", is("testValue")));

        verify(rtsService).findRtsStudentPackageAttributes(any(String.class), any(int.class), captor.capture());

        assertThat(captor.getValue()).containsExactly("testName", "otherName");
    }

    @Test
    public void shouldReturnBadRequestIfAttributesNotGiven() throws Exception {
        http.perform(get(new URI("/students/1/rts/client/attributes"))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isBadRequest());

        verifyZeroInteractions(rtsService);
    }
}
