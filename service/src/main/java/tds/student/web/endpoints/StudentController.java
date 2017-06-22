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

package tds.student.web.endpoints;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.MatrixVariable;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import tds.common.web.exceptions.NotFoundException;
import tds.student.RtsStudentPackageAttribute;
import tds.student.Student;
import tds.student.services.RtsService;
import tds.student.services.StudentService;

@RestController
class StudentController {
    private final StudentService studentService;
    private final RtsService rtsService;

    @Autowired
    public StudentController(StudentService studentService,
                             RtsService rtsService) {
        this.studentService = studentService;
        this.rtsService = rtsService;
    }

    /**
     * Finds the bare minimum information for a student
     *
     * @param id student id
     * @return Student if found otherwise throws a {@link tds.common.web.exceptions.NotFoundException}
     * @throws tds.common.web.exceptions.NotFoundException when student is not found
     */
    @RequestMapping(value = "students/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @Deprecated
    ResponseEntity<Student> findStudentById(@PathVariable final long id) {
        final Student student = studentService.findStudentById(id)
            .orElseThrow(() -> new NotFoundException("Could not find student with id %d", id));

        return ResponseEntity.ok(student);
    }

    @RequestMapping(value = "students/{id}/rts/{clientName}/{attributes}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<List<RtsStudentPackageAttribute>> findRtsStudentPackageAttributes(@PathVariable final long id,
                                                                                     @PathVariable final String clientName,
                                                                                     @MatrixVariable(required = false) final String[] attributes) {
        if (attributes == null || attributes.length == 0) {
            throw new IllegalArgumentException("attributes with values is required");
        }

        return ResponseEntity.ok(rtsService.findRtsStudentPackageAttributes(clientName, id, attributes));
    }

    @RequestMapping(value = "{clientName}/students/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    ResponseEntity<Student> findStudent(@PathVariable final String clientName, @PathVariable final long id) {
        final Student student = rtsService.findStudent(clientName, id)
            .orElseThrow(() -> new NotFoundException("Could not find student with id %d", id));

        return ResponseEntity.ok(student);
    }
}
