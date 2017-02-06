package tds.student;

import org.junit.Test;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {
    @Test
    public void shouldCreateStudent() {
        tds.dll.common.rtspackage.student.data.Student studentPackage = new tds.dll.common.rtspackage.student.data.Student();
        RtsStudentPackageAttribute attr = new RtsStudentPackageAttribute("lang", "eng");
        RtsStudentPackageRelationship rel = new RtsStudentPackageRelationship("school", "schoolName", "Yale", "Yale1");
        Student student = new Student.Builder(1, "SBAC_PT")
            .withLoginSSID("login")
            .withStateCode("CA")
            .withStudentPackage(studentPackage)
            .withAttributes(Collections.singletonList(attr))
            .withRelationships(Collections.singletonList(rel))
            .build();

        assertThat(student.getId()).isEqualTo(1);
        assertThat(student.getClientName()).isEqualTo("SBAC_PT");
        assertThat(student.getStudentPackage()).isEqualTo(studentPackage);
        assertThat(student.getStateCode()).isEqualTo("CA");
        assertThat(student.getLoginSSID()).isEqualTo("login");
        assertThat(student.getRelationships()).containsExactly(rel);
        assertThat(student.getAttributes()).containsExactly(attr);
    }
}