package tds.student.services.impl;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

import tds.dll.common.rtspackage.common.exception.RtsPackageWriterException;
import tds.dll.common.rtspackage.student.StudentPackageWriter;
import tds.student.RtsStudentPackageAttribute;
import tds.student.Student;
import tds.student.model.RtsStudentInfo;
import tds.student.repositories.RtsStudentPackageQueryRepository;
import tds.student.services.RtsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RtsServiceImplTest {
    @Mock
    private RtsStudentPackageQueryRepository rtsRepository;

    private RtsService rtsService;

    private byte[] studentPackageBytes;

    @Before
    public void setUp() throws IOException, RtsPackageWriterException {
        rtsService = new RtsServiceImpl(rtsRepository);
        try (InputStream inputStream = getClass().getClassLoader().getResourceAsStream("studentPackage.xml")) {
            String studentPackage = IOUtils.toString(inputStream);
            StudentPackageWriter writer = new StudentPackageWriter();
            writer.writeObject(studentPackage);
            studentPackageBytes = IOUtils.toByteArray(writer.getInputStream());
        }
    }

    @Test
    public void shouldReturnEmptyIfPackageCannotBeFound() {
        when(rtsRepository.findRtsStudentPackage("client", 1)).thenReturn(Optional.empty());

        assertThat(rtsService.findRtsStudentPackageAttributes("client", 1, new String[]{"attribute"})).isEmpty();
    }

    @Test
    public void shouldReturnEmptyIfPackageCannotBeRead() throws Exception {
        when(rtsRepository.findRtsStudentPackage("client", 1)).thenReturn(Optional.of(studentPackageBytes));

        assertThat(rtsService.findRtsStudentPackageAttributes("client", 1, new String[]{"attribute"})).isEmpty();
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowWhenThereIsExceptionReadingPackage() throws Exception {
        when(rtsRepository.findRtsStudentPackage("client", 1)).thenReturn(Optional.of(new byte[]{}));
        rtsService.findRtsStudentPackageAttributes("client", 1, new String[]{"attribute"});
    }

    @Test
    public void shouldReturnAttributeWhenFoundInPackage() throws Exception {
        when(rtsRepository.findRtsStudentPackage("client", 1)).thenReturn(Optional.of(studentPackageBytes));

        List<RtsStudentPackageAttribute> attributes = rtsService.findRtsStudentPackageAttributes("client", 1, new String[]{RtsStudentPackageAttribute.EXTERNAL_ID});
        assertThat(attributes).isNotEmpty();
        assertThat(attributes.get(0).getName()).isEqualTo(RtsStudentPackageAttribute.EXTERNAL_ID);
        assertThat(attributes.get(0).getValue()).isEqualTo("ATESTASL");
    }

    @Test
    public void shouldReturnEmptyIfAttributeCouldNotBeFoundInPackage() throws Exception {
        when(rtsRepository.findRtsStudentPackage("client", 1)).thenReturn(Optional.of(studentPackageBytes));

        List<RtsStudentPackageAttribute> attributes = rtsService.findRtsStudentPackageAttributes("client", 1, new String[]{"bogus "});
        assertThat(attributes).isEmpty();
    }

    @Test
    public void shouldReturnStudent() {
        RtsStudentInfo info = new RtsStudentInfo(
            1,
            "loginSSID",
            "CA",
            "SBAC_PT",
            studentPackageBytes
        );

        when(rtsRepository.findStudentInfo("SBAC_PT", 1)).thenReturn(Optional.of(info));

        Optional<Student> maybeStudent = rtsService.findStudent("SBAC_PT", 1);

        Student student = maybeStudent.get();

        assertThat(student.getId()).isEqualTo(1);
        assertThat(student.getLoginSSID()).isEqualTo("loginSSID");
        assertThat(student.getStateCode()).isEqualTo("CA");
        assertThat(student.getClientName()).isEqualTo("SBAC_PT");
        assertThat(student.getStudentPackage()).isNotNull();
        assertThat(student.getRelationships()).hasSize(6);
        assertThat(student.getAttributes()).hasSize(27);
    }
}
