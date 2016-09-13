package tds.student.web.endpoints;

import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class StudentControllerIntegrationTests {
    @Test
    public void shouldReturnStudent() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get(String.format("/student/%d", 1))
        .then()
            .contentType(ContentType.JSON)
            .statusCode(200)
            .body("student.id", equalTo(1))
            .body("student.studentId", equalTo("adv001"))
            .body("student.stateCode", equalTo("CA"))
            .body("student.clientName", equalTo("SBAC_PT"));
    }

    @Test
    public void shouldReturnNotFound() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get(String.format("/student/%s", 999999))
        .then()
            .statusCode(404);
    }
}
