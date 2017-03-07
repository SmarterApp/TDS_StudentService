package tds.student.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import tds.dll.common.rtspackage.IRtsPackageReader;
import tds.dll.common.rtspackage.student.StudentPackageReader;

@Configuration
public class LegacyConfiguration {
    @Bean
    public IRtsPackageReader getIRtsPackageReader() {
        return new StudentPackageReader();
    }
}
