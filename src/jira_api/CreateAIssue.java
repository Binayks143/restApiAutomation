package jira_api;

import files.ReUseAblemethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class CreateAIssue {
    String id ;
    @Test(priority = 0)
    public void jiralogin()

    {
        RestAssured.baseURI="http://localhost:8080/";

        String res=given().header("Content-Type","application/json").body("{\n" +
                "    \"username\": \"vivnay111\",\n" +
                "    \"password\": \"Binay123@\"\n" +
                "}").log().all().when().post("rest/auth/1/session").then().assertThat().
                statusCode(200).log().all().extract().response().asString();

        JsonPath js= ReUseAblemethods.rowToJson(res);
         id=js.getString("session.value");
        System.out.println("JSESSIONID="+id);
    }

    @Test(priority = 1)
            public void createaissue()
    {
        jiralogin();
        RestAssured.baseURI="http://localhost:8080/";
       String temp=given().header("Content-Type","application/json").header("Cookie","JSESSIONID="+id)
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "        \"project\": {\n" +
                        "            \"key\":\"RSAT\"\n" +
                        "        \n" +
                        "        },\n" +
                        "        \"summary\": \"page is not working\",\n" +
                        "        \"issuetype\": {\n" +
                        "            \"id\": \"10004\"\n" +
                        "        },\n" +
                        "        \"description\": \"page is not working when selecting the box\"\n" +
                        "    }\n" +
                        "}").log().all().when().post("rest/api/2/issue").then().statusCode(201).log().all()
                .extract().response().asString();

    }

    @Test(priority = 2)
    //Here using pathParam to give the variable input in URI
    public void addComment()
    {
        jiralogin();
        given().pathParam("key","10112").header("Content-Type","application/json")
                .header("Cookie","JSESSIONID="+id)
                .body("{\n" +
                        "    \"body\": \"Again issue is coming 3\"\n" +
                        "}").log().all().when().post("rest/api/2/issue/{key}/comment")
                .then().assertThat().statusCode(201).log().all();

    }
}
