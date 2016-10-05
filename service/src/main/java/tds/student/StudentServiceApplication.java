package tds.student;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import tds.dll.common.rtspackage.IRtsPackageReader;
import tds.dll.common.rtspackage.student.StudentPackageReader;

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
