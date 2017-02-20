package tds.student.repositories.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.support.AbstractLobCreatingPreparedStatementCallback;
import org.springframework.jdbc.support.lob.DefaultLobHandler;
import org.springframework.jdbc.support.lob.LobCreator;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import tds.student.model.RtsStudentInfo;
import tds.student.repositories.RtsStudentPackageQueryRepository;
import tds.student.util.StudentPackageLoader;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RtsStudentPackageQueryRepositoryImplIntegrationTests {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RtsStudentPackageQueryRepository repository;

    private byte[] studentPackageBytes;

    @Before
    public void setUp() {
        studentPackageBytes = StudentPackageLoader.getStudentPackageBytesFromClassPath(getClass(), "studentPackage.xml");
        insertData();
    }

    @Test
    public void shouldGetRtsStudentPackageInfo() {
        Optional<RtsStudentInfo> maybeRtsStudentInfo = repository.findStudentInfo("SBAC_PT", 1);

        RtsStudentInfo info = maybeRtsStudentInfo.get();
        assertThat(info.getClientName()).isEqualTo("SBAC_PT");
        assertThat(info.getId()).isEqualTo(1);
        assertThat(info.getLoginSSID()).isEqualTo("adv001");
        assertThat(info.getStateCode()).isEqualTo("CA");
        assertThat(info.getStudentPackage()).isEqualTo(studentPackageBytes);
    }

    @Test
    public void shouldBeEmptyWhenRtsStudentPackageInfoCannotBeFound() {
        Optional<RtsStudentInfo> maybeRtsStudentInfo = repository.findStudentInfo("SBAC", 1);
        assertThat(maybeRtsStudentInfo).isNotPresent();
    }

    @Test
    public void shouldFindRtsPackageBytes() {
        Optional<byte[]> maybePackage = repository.findRtsStudentPackage("SBAC_PT", 1);
        assertThat(maybePackage.get()).isEqualTo(studentPackageBytes);
    }

    @Test
    public void shouldBeEmptyWhenPackageCannotBeFound() {
        Optional<byte[]> maybePackage = repository.findRtsStudentPackage("SBAC", 1);
        assertThat(maybePackage).isNotPresent();
    }

    private void insertData() {
        String studentKeySQL = "insert into r_studentkeyid values (1, 'adv001', 'CA', 'SBAC_PT')";

        String SQL = "insert into r_studentpackage (_key, studentkey, clientname, package, version, datecreated) \n" +
            "values (?, ?, ?, ?, ?, NOW());";

        jdbcTemplate.execute(studentKeySQL);

        jdbcTemplate.execute(
            SQL,
            new AbstractLobCreatingPreparedStatementCallback(new DefaultLobHandler()) {
                protected void setValues(PreparedStatement ps, LobCreator lobCreator) throws SQLException {
                    ps.setLong(1, 1);
                    ps.setLong(2, 1);
                    ps.setString(3, "SBAC_PT");
                    lobCreator.setBlobAsBytes(ps, 4, studentPackageBytes);
                    ps.setString(5, "1");
                }
            }
        );
    }
}
