package tds.student.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

import tds.common.data.mysql.UuidAdapter;
import tds.common.data.mysql.spring.UuidBeanPropertyRowMapper;
import tds.student.Student;
import tds.student.repositories.StudentRepository;

@Repository
public class StudentRepositoryImpl implements StudentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public StudentRepositoryImpl(final DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public Optional<Student> getStudentById(long id) {
        return Optional.of(new Student());
    }
}
