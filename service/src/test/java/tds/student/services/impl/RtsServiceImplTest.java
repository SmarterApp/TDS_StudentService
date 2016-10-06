package tds.student.services.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import tds.dll.common.rtspackage.IRtsPackageReader;
import tds.dll.common.rtspackage.common.exception.RtsPackageReaderException;
import tds.student.RtsAttribute;
import tds.student.repositories.RtsStudentPackageQueryRepository;
import tds.student.services.RtsService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RtsServiceImplTest {
    @Mock
    private RtsStudentPackageQueryRepository rtsRepository;

    @Mock
    private IRtsPackageReader packageReader;

    private RtsService rtsService;

    @Before
    public void setUp() {
        rtsService = new RtsServiceImpl(rtsRepository, packageReader);
    }

    @Test
    public void shouldReturnEmptyIfPackageCannotBeFound() {
        when(rtsRepository.findRtsStudentPackage("client", 1)).thenReturn(Optional.empty());

        assertThat(rtsService.findRtsStudentPackageAttribute("client", 1, "attribute")).isNotPresent();
    }

    @Test
    public void shouldReturnEmptyIfPackageCannotBeRead() throws Exception {
        byte[] blob = new byte[]{};
        when(rtsRepository.findRtsStudentPackage("client", 1)).thenReturn(Optional.of(blob));
        when(packageReader.read(blob)).thenReturn(false);

        assertThat(rtsService.findRtsStudentPackageAttribute("client", 1, "attribute")).isNotPresent();

        verify(packageReader).read(blob);
    }

    @Test(expected = RuntimeException.class)
    public void shouldThrowWhenThereIsExceptionReadingPackage() throws Exception {
        Optional<byte[]> maybeBlob = Optional.of(new byte[]{});
        when(rtsRepository.findRtsStudentPackage("client", 1)).thenReturn(maybeBlob);
        when(packageReader.read(maybeBlob.get())).thenThrow(new RtsPackageReaderException("Fail"));
        rtsService.findRtsStudentPackageAttribute("client", 1, "attribute");
    }

    @Test
    public void shouldReturnAttributeWhenFoundInPackage() throws Exception {
        byte[] blob = new byte[]{};
        when(rtsRepository.findRtsStudentPackage("client", 1)).thenReturn(Optional.of(blob));
        when(packageReader.read(blob)).thenReturn(true);
        when(packageReader.getFieldValue("attribute")).thenReturn("value");

        Optional<RtsAttribute> maybeAttribute = rtsService.findRtsStudentPackageAttribute("client", 1, "attribute");
        assertThat(maybeAttribute).isPresent();
        assertThat(maybeAttribute.get().getName()).isEqualTo("attribute");
        assertThat(maybeAttribute.get().getValue()).isEqualTo("value");
    }

    @Test
    public void shouldReturnEmptyIfAttributeCouldNotBeFoundInPackage() throws Exception {
        byte[] blob = new byte[]{};
        when(rtsRepository.findRtsStudentPackage("client", 1)).thenReturn(Optional.of(blob));
        when(packageReader.read(blob)).thenReturn(true);

        Optional<RtsAttribute> maybeAttribute = rtsService.findRtsStudentPackageAttribute("client", 1, "attribute");
        assertThat(maybeAttribute).isNotPresent();
    }
}
