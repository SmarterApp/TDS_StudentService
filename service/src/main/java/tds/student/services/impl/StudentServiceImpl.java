package tds.student.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Optional;

import tds.common.cache.CacheType;
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
    @Cacheable(CacheType.LONG_TERM)
    public Optional<Student> findStudentById(long id) {
        return studentRepository.findStudentById(id);
    }
}
