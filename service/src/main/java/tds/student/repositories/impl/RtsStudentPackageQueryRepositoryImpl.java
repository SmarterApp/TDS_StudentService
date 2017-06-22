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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import tds.student.model.RtsStudentInfo;
import tds.student.repositories.RtsStudentPackageQueryRepository;

@Repository
public class RtsStudentPackageQueryRepositoryImpl implements RtsStudentPackageQueryRepository {
    private static final Logger LOG = LoggerFactory.getLogger(RtsStudentPackageQueryRepositoryImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LobHandler lobHandler;

    @Autowired
    public RtsStudentPackageQueryRepositoryImpl(final NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        lobHandler = new DefaultLobHandler();
    }

    @Override
    public Optional<byte[]> findRtsStudentPackage(final String clientName, final long studentId) {
        //Legacy Query from RtsPackageDLL.getPackageByKeyAndClientName
        SqlParameterSource parameters = new MapSqlParameterSource("clientName", clientName)
            .addValue("studentId", studentId);

        final String SQL = "SELECT package " +
            "FROM r_studentpackage \n" +
            "WHERE studentkey = :studentId " +
            "AND ClientName = :clientName";

        Optional<byte[]> maybePackage = Optional.empty();
        try {
            byte[] studentPackage = jdbcTemplate.queryForObject(SQL, parameters, (rs, rowNum) ->
                lobHandler.getBlobAsBytes(rs, "package")
            );

            maybePackage = Optional.of(studentPackage);
        } catch (EmptyResultDataAccessException e) {
            LOG.debug("Unable to find package for key %s and value %s", clientName, studentId);
        }

        return maybePackage;
    }

    @Override
    public Optional<RtsStudentInfo> findStudentInfo(final String clientName, final long studentId) {
        final SqlParameterSource parameters = new MapSqlParameterSource("id", studentId)
            .addValue("clientName", clientName);

        String query = "SELECT " +
            "  sk.studentkey AS id, \n" +
            "  sk.studentid, \n " +
            "  sk.statecode, \n " +
            "  sk.clientname, \n " +
            "  rs.package \n" +
            "FROM r_studentkeyid sk \n" +
            "JOIN r_studentpackage rs \n" +
            "  ON rs.studentkey = sk.studentkey \n" +
            "  AND rs.ClientName = sk.clientName \n" +
            "WHERE sk.studentkey = :id \n" +
            "AND sk.clientName = :clientName ";

        Optional<RtsStudentInfo> student;
        try {
            student = Optional.of(jdbcTemplate.queryForObject(query, parameters, (rs, rowNum) -> new RtsStudentInfo(rs.getLong("id"),
                rs.getString("studentid"),
                rs.getString("statecode"),
                rs.getString("clientname"),
                lobHandler.getBlobAsBytes(rs, "package")
                )));
        } catch (EmptyResultDataAccessException e) {
            student = Optional.empty();
        }

        return student;
    }
}
