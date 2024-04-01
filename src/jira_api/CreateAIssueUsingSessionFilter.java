package jira_api;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class CreateAIssueUsingSessionFilter {

   /*Storing Session Information: The SessionFilter is used to store session information or cookies obtained
    during the login/authentication process. In your case, it appears that you are using Jira's REST API
    to authenticate a user and establish a session. By using a SessionFilter, you can capture the session
    information (typically in the form of cookies) and reuse it in subsequent API requests. */

    private SessionFilter session;

    //By storing the session information in a class-level variable like private SessionFilter session;
    // you can reuse it across multiple test methods within the same class.

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = "http://localhost:8080/";
        session = new SessionFilter();

        // Authenticate and store the session using the session filter
        given()
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"username\": \"vivnay111\",\n" +
                        "    \"password\": \"Binay123@\"\n" +
                        "}")
                .filter(session).log().all()
                .when()
                .post("rest/auth/1/session")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void createIssue() {
        given()
                .pathParam("key","10112")
                .header("Content-Type", "application/json")
                .body("{\n" +
                        "    \"body\": \"Again issue is coming in the flow\"\n" +
                        "\n" +
                        "}")
                .filter(session).log().all()
                .when()
                .post("rest/api/2/issue/{key}/comment")
                .then()
                .log().all()
                .statusCode(201);
    }
//GET Comments
    @Test
    public void getComments()
    {
        given()
                .pathParam("key","10112")
                .log().all()
                .filter(session)
                .when()
                .get("/rest/api/2/issue/{key}/comment")
                .then()
                .log().all()
                .statusCode(200);
    }
}
