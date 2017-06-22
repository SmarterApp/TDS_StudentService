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

package tds.student;

import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {
    @Test
    public void shouldCreateStudent() {
        RtsStudentPackageAttribute attr = new RtsStudentPackageAttribute("lang", "eng");
        RtsStudentPackageRelationship rel = new RtsStudentPackageRelationship("school", "schoolName", "Yale", "Yale1");
        Student student = new Student.Builder(1, "SBAC_PT")
            .withLoginSSID("login")
            .withStateCode("CA")
            .withAttributes(Collections.singletonList(attr))
            .withRelationships(Collections.singletonList(rel))
            .build();

        assertThat(student.getId()).isEqualTo(1);
        assertThat(student.getClientName()).isEqualTo("SBAC_PT");
        assertThat(student.getStateCode()).isEqualTo("CA");
        assertThat(student.getLoginSSID()).isEqualTo("login");
        assertThat(student.getRelationships()).containsExactly(rel);
        assertThat(student.getAttributes()).containsExactly(attr);
    }
}