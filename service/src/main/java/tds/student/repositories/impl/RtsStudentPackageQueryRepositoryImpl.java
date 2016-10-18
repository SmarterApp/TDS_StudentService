package tds.student.repositories.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobHandler;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Optional;

import tds.student.repositories.RtsStudentPackageQueryRepository;

@Repository
public class RtsStudentPackageQueryRepositoryImpl implements RtsStudentPackageQueryRepository {
    private static final Logger LOG = LoggerFactory.getLogger(RtsStudentPackageQueryRepositoryImpl.class);
    private final NamedParameterJdbcTemplate jdbcTemplate;
    private final LobHandler lobHandler;

    public RtsStudentPackageQueryRepositoryImpl(DataSource dataSource) {
        this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        lobHandler = new DefaultLobHandler();
    }

    @Override
    public Optional<byte[]> findRtsStudentPackage(String clientName, long studentId) {
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
}
