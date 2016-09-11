package tds.student.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

import tds.student.Student;
import tds.student.repositories.StudentRepository;
import tds.student.services.StudentService;

@Service
class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Autowired
    public StudentServiceImpl(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    public Optional<Student> getStudent(long id) {
        return studentRepository.getStudentById(id);
    }
}
