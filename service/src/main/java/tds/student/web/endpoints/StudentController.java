package tds.student.web.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import tds.common.web.exceptions.NotFoundException;
import tds.student.RtsStudentPackageAttribute;
import tds.student.Student;
import tds.student.services.RtsService;
import tds.student.services.StudentService;

@RestController
@RequestMapping("/students")
class StudentController {
    private final StudentService studentService;
    private final RtsService rtsService;

    @Autowired
    public StudentController(StudentService studentService,
                             RtsService rtsService) {
        this.studentService = studentService;
        this.rtsService = rtsService;
    }

    @RequestMapping(value = "{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Student> findStudentById(@PathVariable long id) {
        final Student student = studentService.findStudentById(id)
            .orElseThrow(() -> new NotFoundException("Could not find student with id %d", id));

        return ResponseEntity.ok(student);
    }

    @RequestMapping(value = "{id}/rts/{clientName}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<RtsStudentPackageAttribute>> findRtsStudentPackageAttributes(@PathVariable long id,
                                                                                     @PathVariable String clientName,
                                                                                     @RequestParam String[] attributeName) {

        return ResponseEntity.ok(rtsService.findRtsStudentPackageAttributes(clientName, id, attributeName));
    }
}
