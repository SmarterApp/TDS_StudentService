package tds.student.web.endpoints;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

import tds.common.web.exceptions.NotFoundException;
import tds.student.Student;
import tds.student.services.StudentService;
import tds.student.web.resources.StudentResource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class StudentControllerTest {
    private StudentService studentService;
    private StudentController controller;

    @Before
    public void setUp() {
        HttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        studentService = mock(StudentService.class);
        controller = new StudentController(studentService);
    }

    @Test
    public void itShouldReturnStudentResourceById() {
        Student student = new Student(1, "testId", "CA", "clientName");

        when(studentService.findStudentById(1)).thenReturn(Optional.of(student));
        ResponseEntity<StudentResource> studentResponse = controller.findStudentById(1);
        verify(studentService).findStudentById(1);

        assertThat(studentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentResponse.getBody().getId().getHref()).isEqualTo("http://localhost/students/1");
        assertThat(studentResponse.getBody().getStudent()).isEqualTo(student);
    }

    @Test(expected = NotFoundException.class)
    public void itShouldHandleStudentByIdNotFound() {
        when(studentService.findStudentById(1)).thenReturn(Optional.empty());
        controller.findStudentById(1);
    }
}
