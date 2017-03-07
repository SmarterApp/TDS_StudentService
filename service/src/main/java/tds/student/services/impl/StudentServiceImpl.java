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
    public StudentServiceImpl(final StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    @Override
    @Cacheable(CacheType.LONG_TERM)
    @Deprecated
    public Optional<Student> findStudentById(final long id) {
        return studentRepository.findStudentById(id);
    }
}
