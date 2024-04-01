package jira_api;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GetIssue {
    private SessionFilter session;

    @BeforeClass
    public void Setup()
    {
        RestAssured.baseURI="http://localhost:8080/";
        session= new SessionFilter();

        given().header("Content-Type","application/json")
                .body("{\n" +
                        "    \"username\": \"vivnay111\",\n" +
                        "    \"password\": \"Binay123@\"\n" +
                        "}")
                .log().all().filter(session)
                .when().post("rest/auth/1/session")
                .then().statusCode(200)
                .log().all();
    }
    @Test
    public void getIssueDetails()
    {
     given().pathParam("issueIdOrKey","RSAT-17")
             .queryParam("fields","issuetype,priority").log().all()
             .filter(session)
             .when().get("rest/api/2/issue/{issueIdOrKey}")
             .then().log().all()
             .statusCode(200);
    }

}
