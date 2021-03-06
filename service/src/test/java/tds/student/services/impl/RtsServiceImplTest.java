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

package tds.student.services.impl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;
import java.util.Optional;

import tds.student.RtsStudentPackageAttribute;
import tds.student.RtsStudentPackageRelationship;
import tds.student.Student;
import tds.student.model.RtsStudentInfo;
import tds.student.repositories.RtsStudentPackageQueryRepository;
import tds.student.services.RtsService;
import tds.student.util.StudentPackageLoader;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class RtsServiceImplTest {
    @Mock
    private RtsStudentPackageQueryRepository rtsRepository;

    private RtsService rtsService;

    private byte[] studentPackageBytes;

    @Before
    public void setUp() {
        rtsService = new RtsServiceImpl(rtsRepository);
        studentPackageBytes = StudentPackageLoader.getStudentPackageBytesFromClassPath(getClass(), "studentPackage.xml");
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
        assertThat(student.getRelationships()).hasSize(6);
        assertThat(student.getAttributes()).hasSize(27);

        Optional<String> stateAbbrev = student.getRelationships().stream()
            .filter(rel -> rel.getId().equals("StateAbbreviation"))
            .map(RtsStudentPackageRelationship::getValue)
            .findFirst();

        assertThat(stateAbbrev).isPresent();
        assertThat(stateAbbrev.get()).isEqualTo("OR");
    }
}
