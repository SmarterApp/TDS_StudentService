package tds.student.web.endpoints;


import com.jayway.restassured.http.ContentType;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.jayway.restassured.RestAssured.given;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest (webEnvironment= SpringBootTest.WebEnvironment.DEFINED_PORT)
public class HealthControllerIntegrationTests {
    @Test
    public void shouldReturnOk() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("/student/isAlive")
        .then()
            .contentType(ContentType.JSON)
            .statusCode(200);
    }
}

