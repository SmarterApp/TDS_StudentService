package tds.student.web.endpoints;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.net.URI;
import java.util.Optional;

import tds.student.RtsAttribute;
import tds.student.Student;
import tds.student.services.RtsService;
import tds.student.services.StudentService;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(StudentController.class)
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
    public void shouldReturnRtsAttributeForStudent() throws Exception {
        when(rtsService.findRtsStudentPackageAttribute("client", 1, "testName")).thenReturn(Optional.of(new RtsAttribute("testName", "testValue")));

        http.perform(get(new URI("/students/1/rts/client/testName"))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk())
            .andExpect(jsonPath("name", is("testName")))
            .andExpect(jsonPath("value", is("testValue")));
    }

    @Test
    public void shouldReturnNotFoundWhenAttributeNotFound() throws Exception {
        when(rtsService.findRtsStudentPackageAttribute("client", 1, "name")).thenReturn(Optional.empty());
        http.perform(get(new URI("/students/1/rts/client/name"))
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNotFound());

        verify(rtsService).findRtsStudentPackageAttribute("client", 1, "name");
    }
}
