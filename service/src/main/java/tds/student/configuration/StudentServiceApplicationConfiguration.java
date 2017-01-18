package tds.student.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import tds.common.configuration.CacheConfiguration;
import tds.common.configuration.DataSourceConfiguration;
import tds.common.configuration.JacksonObjectMapperConfiguration;
import tds.common.web.advice.ExceptionAdvice;

/**
 * This is the base configuration class for the student microservice
 */
@Configuration
@Import({
    ExceptionAdvice.class,
    DataSourceConfiguration.class,
    JacksonObjectMapperConfiguration.class,
    CacheConfiguration.class
})
public class StudentServiceApplicationConfiguration {
}
