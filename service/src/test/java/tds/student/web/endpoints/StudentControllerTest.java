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
import java.util.List;
import java.util.Optional;

import tds.common.web.exceptions.NotFoundException;
import tds.student.RtsStudentPackageAttribute;
import tds.student.Student;
import tds.student.services.RtsService;
import tds.student.services.StudentService;

import static java.util.Arrays.asList;
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
        Student student = new Student.Builder(1, "clientName")
            .withStateCode("CA")
            .withLoginSSID("testId")
            .build();

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

    @Test(expected = IllegalArgumentException.class)
    public void shouldReturnSingleRtsAttribute() {
        controller.findRtsStudentPackageAttributes(1, "client", null);
    }

    @Test
    public void shouldReturnRtsAttributes() {
        RtsStudentPackageAttribute attribute = new RtsStudentPackageAttribute("name", "value");
        RtsStudentPackageAttribute attribute2 = new RtsStudentPackageAttribute("name2", "value");

        when(rtsService.findRtsStudentPackageAttributes("client", 1, new String[]{"name", "name2"})).thenReturn(asList(attribute, attribute2));
        ResponseEntity<List<RtsStudentPackageAttribute>> response = controller.findRtsStudentPackageAttributes(1, "client", new String[]{"name", "name2"});
        verify(rtsService).findRtsStudentPackageAttributes("client", 1, new String[]{"name", "name2"});

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).hasSize(2);
        assertThat(response.getBody().get(0)).isEqualTo(attribute);
        assertThat(response.getBody().get(1)).isEqualTo(attribute2);
    }

    @Test
    public void shouldReturnStudent() {
        Student student = new Student.Builder(1, "SBAC_PT").build();

        when(rtsService.findStudent("SBAC_PT", 1)).thenReturn(Optional.of(student));

        ResponseEntity<Student> entity = controller.findStudent("SBAC_PT", 1);
        verify(rtsService).findStudent("SBAC_PT", 1);

        assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(entity.getBody()).isEqualTo(student);
    }
}
