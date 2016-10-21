package tds.student.web.endpoints;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import tds.common.web.exceptions.NotFoundException;
import tds.student.RtsStudentPackageAttribute;
import tds.student.Student;
import tds.student.services.RtsService;
import tds.student.services.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class StudentControllerTest {
    private StudentController controller;

    @Mock
    private StudentService studentService;

    @Mock
    private RtsService rtsService;

    @Before
    public void setUp() {
        HttpServletRequest request = new MockHttpServletRequest();
        ServletRequestAttributes requestAttributes = new ServletRequestAttributes(request);
        RequestContextHolder.setRequestAttributes(requestAttributes);

        controller = new StudentController(studentService, rtsService);
    }

    @Test
    public void shouldReturnStudentResourceById() {
        Student student = new Student(1, "testId", "CA", "clientName");

        when(studentService.findStudentById(1)).thenReturn(Optional.of(student));
        ResponseEntity<Student> studentResponse = controller.findStudentById(1);
        verify(studentService).findStudentById(1);

        assertThat(studentResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(studentResponse.getBody()).isEqualTo(student);
    }

    @Test(expected = NotFoundException.class)
    public void shouldHandleStudentByIdNotFound() {
        when(studentService.findStudentById(1)).thenReturn(Optional.empty());
        controller.findStudentById(1);
    }

    @Test
    public void shouldReturnRtsAttribute() {
        RtsStudentPackageAttribute attribute = new RtsStudentPackageAttribute("name", "value");

        when(rtsService.findRtsStudentPackageAttributes("client", 1, new String[]{"name"})).thenReturn(Collections.singletonList(attribute));
        ResponseEntity<List<RtsStudentPackageAttribute>> response = controller.findRtsStudentPackageAttributes(1, "client", new String[]{"name"});
        verify(rtsService).findRtsStudentPackageAttributes("client", 1, new String[]{"name"});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(1);
        assertThat(response.getBody().get(0)).isEqualTo(attribute);
    }
}
