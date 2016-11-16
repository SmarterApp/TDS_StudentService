package tds.student.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import tds.common.web.advice.ExceptionAdvice;

/**
 * This is the base configuration class for the student microservice
 */
@Configuration
@Import({ExceptionAdvice.class})
public class StudentServiceApplicationConfiguration {
}
