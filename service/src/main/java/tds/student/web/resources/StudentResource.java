package tds.student.web.resources;

import org.springframework.hateoas.ResourceSupport;

import tds.student.Student;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class StudentResource extends ResourceSupport {
    private final Student student;

    public StudentResource(Student student) {
        this.student = student;
//        this.add(linkTo(
//            methodOn(SessionController.class)
//                .getSession(session.getId()))
//            .withSelfRel());
    }

    public Student getStudent() {
        return student;
    }
}
