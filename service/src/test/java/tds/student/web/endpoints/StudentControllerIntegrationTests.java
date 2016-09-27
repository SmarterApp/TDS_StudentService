package tds.student.web.endpoints;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import tds.student.StudentServiceApplication;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = StudentServiceApplication.class)
@WebAppConfiguration
@IntegrationTest("server.port:8080")
public class StudentControllerIntegrationTests {
    @Test
    public void shouldReturnStudent() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get(String.format("/students/%d", 1))
        .then()
            .contentType(ContentType.JSON)
            .statusCode(200)
            .body("student.id", equalTo(1))
            .body("student.loginSSID", equalTo("adv001"))
            .body("student.stateCode", equalTo("CA"))
            .body("student.clientName", equalTo("SBAC_PT"))
            .body("_links.self.href", equalTo("http://localhost:8080/students/1"));

    }

    @Test
    public void shouldReturnNotFound() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get(String.format("/students/%s", 999999))
        .then()
            .statusCode(404);
    }
}
