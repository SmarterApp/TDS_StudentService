package tds.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tds.student.legacy.rtspackage.IRtsPackageReader;
import tds.student.legacy.rtspackage.student.StudentPackageReader;

@SpringBootApplication
public class StudentServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StudentServiceApplication.class, args);
    }

    @Bean
    public IRtsPackageReader getIRtsPackageReader() {
        return new StudentPackageReader();
    }
}
