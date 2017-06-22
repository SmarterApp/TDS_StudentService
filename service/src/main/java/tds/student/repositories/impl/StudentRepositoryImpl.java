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
    @Deprecated
    public Optional<Student> findStudentById(final long id) {
        final SqlParameterSource parameters = new MapSqlParameterSource("id", id);

        String query = "SELECT studentkey AS id, studentid, statecode, clientname \n" +
            "FROM session.r_studentkeyid \n" +
            "WHERE studentkey = :id";

        Optional<Student> student;
        try {
            student = Optional.of(jdbcTemplate.queryForObject(query, parameters, (rs, rowNum) -> new Student.Builder(rs.getLong("id"), rs.getString("clientname"))
                .withStateCode(rs.getString("stateCode"))
                .withLoginSSID(rs.getString("studentid"))
                .build()
            ));
        } catch (EmptyResultDataAccessException e) {
            student = Optional.empty();
        }

        return student;
    }
}
