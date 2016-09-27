package tds.student;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {

    @Test
    public void shouldBeCreated() {
        Student student = new Student(1, "test", "CA", "clientName");

        assertThat(student.getId()).isEqualTo(1);
        assertThat(student.getClientName()).isEqualTo("clientName");
        assertThat(student.getStateCode()).isEqualTo("CA");
        assertThat(student.getLoginSSID()).isEqualTo("test");
    }
}
