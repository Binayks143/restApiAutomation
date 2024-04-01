package jira_api;

import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class AddAttachment {

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
    public void add_attachment()
    {
        given().header("Content-Type","multipart/form-data")
                .header("X-Atlassian-Token","no-check")
                .pathParam("key","RSAT-17")
                .filter(session)
                .multiPart("file",new File("C:\\Users\\SC-229-USER\\Desktop\\Screenshot_6.png"))
                .log().all()
                .when()
                .post("rest/api/2/issue/{key}/attachments")
                .then()
                .statusCode(200)
                .log().all();
    }
}
