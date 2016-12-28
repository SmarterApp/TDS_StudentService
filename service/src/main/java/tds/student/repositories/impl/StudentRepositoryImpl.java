package tds.student.repositories.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import tds.student.Student;
import tds.student.repositories.StudentRepository;

@Repository
class StudentRepositoryImpl implements StudentRepository {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    @Autowired
    public StudentRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Student> findStudentById(long id) {
        final SqlParameterSource parameters = new MapSqlParameterSource("id", id);

        String query = "SELECT studentkey AS id, studentid, statecode, clientname \n" +
            "FROM session.r_studentkeyid \n" +
            "WHERE studentkey = :id";

        Optional<Student> student;
        try {
            student = Optional.of(jdbcTemplate.queryForObject(query, parameters, (rs, rowNum) -> new Student(rs.getLong("id"),
                rs.getString("studentid"),
                rs.getString("statecode"),
                rs.getString("clientname"))));
        } catch (EmptyResultDataAccessException e) {
            student = Optional.empty();
        }

        return student;
    }
}
