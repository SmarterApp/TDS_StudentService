package tds.student.web.resources;

import org.springframework.hateoas.ResourceSupport;

import tds.student.Student;
import tds.student.web.endpoints.StudentController;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * HATEOAS resource representing a {@link tds.student.Student}
 */
public class StudentResource extends ResourceSupport {
    private final Student student;

    public StudentResource(Student student) {
        this.student = student;
        this.add(linkTo(
            methodOn(StudentController.class)
                .findStudentById(student.getId()))
            .withSelfRel());
    }

    /**
     * @return the {@link tds.student.Student}
     */
    public Student getStudent() {
        return student;
    }
}
