package tds.student.services.impl;

import org.apache.commons.io.IOUtils;
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

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Optional;

import tds.dll.common.rtspackage.common.exception.RtsPackageWriterException;
import tds.dll.common.rtspackage.student.StudentPackageWriter;
import tds.student.RtsStudentPackageAttribute;
import tds.student.services.RtsService;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class RtsServiceImplIntegrationTests {
    @Autowired
    private DataSource dataSource;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private RtsService rtsService;

    private byte[] studentPackageBytes;

    @Before
    public void setUp() throws Exception {
        jdbcTemplate = new JdbcTemplate(dataSource);
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("studentPackage.xml");
        String studentPackage = IOUtils.toString(inputStream);

        StudentPackageWriter writer = new StudentPackageWriter();
        writer.writeObject(studentPackage);
        studentPackageBytes = IOUtils.toByteArray(writer.getInputStream());
    }

    @Test
    public void shouldReadPackage() throws IOException, RtsPackageWriterException {
        insertData();

        Optional<RtsStudentPackageAttribute> maybeRtsAttribute = rtsService.findRtsStudentPackageAttribute("SBAC_PT", 1, "LglFNm");

        assertThat(maybeRtsAttribute).isPresent();
        assertThat(maybeRtsAttribute.get().getName()).isEqualTo("LglFNm");
        assertThat(maybeRtsAttribute.get().getValue()).isEqualTo("ASL");
    }

    @Test
    public void shouldReturnEmptyOptionalWhenPackageNotFound() {
        Optional<RtsStudentPackageAttribute> maybeRtsAttribute = rtsService.findRtsStudentPackageAttribute("SBAC_PT", 1, "NameOfInstitution");
        assertThat(maybeRtsAttribute).isNotPresent();
    }

    @Test
    public void shouldReturnEmptyWhenAttributeCannotBeFound() throws IOException, RtsPackageWriterException {
        insertData();

        Optional<RtsStudentPackageAttribute> maybeRtsAttribute = rtsService.findRtsStudentPackageAttribute("SBAC_PT", 1, "Bogus");

        assertThat(maybeRtsAttribute).isNotPresent();
    }

    private void insertData() {
        String SQL = "insert into r_studentpackage (_key, studentkey, clientname, package, version, datecreated) \n" +
            "values (?, ?, ?, ?, ?, NOW());";

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
