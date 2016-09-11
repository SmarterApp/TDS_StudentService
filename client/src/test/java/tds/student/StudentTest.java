package tds.student;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class StudentTest {

    @Test
    public void itCanBeCreated() {
        Student student = new Student();

        assertThat(student.getId()).isEqualTo(0);
        student.setId(1);
        assertThat(student.getId()).isEqualTo(1);
    }
}
