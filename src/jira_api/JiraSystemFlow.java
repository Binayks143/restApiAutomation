package jira_api;

import files.ReUseAblemethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

/*
Note : In real time when we try to authenticate our self using login mechanism,
some time Rest assured may not be able to recognise the generated certificate
to bypass this authentication we can add relaxedHTTPSValidation after given().
If we have given this, and we can handle it even there is no proper certificate.
 */

public class JiraSystemFlow {
    private SessionFilter session;
//Setting up the session to use the session in all test cases
    @BeforeClass
    public void Setup()
    {
        RestAssured.baseURI="http://localhost:8080/";
        session=new SessionFilter();

        given().relaxedHTTPSValidation().header("Content-Type","application/json")
                .body("{\n" +
                        "    \"username\": \"vivnay111\",\n" +
                        "    \"password\": \"Binay123@\"\n" +
                        "}")
                .log().all().filter(session)
                .when().post("rest/auth/1/session")
                .then().statusCode(200)
                .log().all();
    }

    // Creating a new issue
    int issue_id;
    @Test(priority = 0)
    public void createaissue()
    {
        String temp=given().header("Content-Type","application/json")
                .filter(session)
                .body("{\n" +
                        "    \"fields\": {\n" +
                        "        \"project\": {\n" +
                        "            \"key\":\"RSAT\"\n" +
                        "        \n" +
                        "        },\n" +
                        "        \"summary\": \"Not able to access the Admin page\",\n" +
                        "        \"issuetype\": {\n" +
                        "            \"id\": \"10004\"\n" +
                        "        },\n" +
                        "        \"description\": \"Getting page not found while accessing the page\"\n" +
                        "    }\n" +
                        "}").log().all().when().post("rest/api/2/issue")
                .then().statusCode(201).log().all()
                .extract().response().asString();
        JsonPath js=ReUseAblemethods.rowToJson(temp);
        issue_id=js.getInt("id");
        System.out.println(issue_id);

    }

    //Adding the comment in a issue
    @Test(priority = 1)
    //Here using pathParam to give the variable input in URI
    public void addComment1()
    {
        given().pathParam("key",issue_id)
                .header("Content-Type","application/json")
                .filter(session)
                .body("{\n" +
                        "    \"body\": \"After fix getting Error while loading the page\"\n" +
                        "}").log().all()
                .when().post("rest/api/2/issue/{key}/comment")
                .then().assertThat().statusCode(201).log().all();
    }

    //Adding the comment in a issue
    @Test(priority = 1)
    //Here using pathParam to give the variable input in URI
    public void addComment2()
    {
        given().pathParam("key",issue_id)
                .header("Content-Type","application/json")
                .filter(session)
                .body("{\n" +
                        "    \"body\": \"Loading Issue\"\n" +
                        "}").log().all()
                .when().post("rest/api/2/issue/{key}/comment")
                .then().assertThat().statusCode(201).log().all();
    }


    @Test(priority = 2)
    // adding the attachment in a existing issue
    public void add_attachment()
    {
        given().header("Content-Type","multipart/form-data")
                .header("X-Atlassian-Token","no-check")
                .pathParam("key",issue_id)
                .filter(session)
                .multiPart("file",new File("C:\\Users\\SC-229-USER\\Desktop\\Screenshot_6.png"))
                .log().all()
                .when()
                .post("rest/api/2/issue/{key}/attachments")
                .then()
                .statusCode(200)
                .log().all();
    }
//getting the required ticket info here checking issue type and priority by giving it in query parameters
    //Query parameters for limit the response
    //if we will not give query parameters all details will be displayed
    @Test (priority = 3)
    public void getTicketDetails()
    {
        given().pathParam("key",issue_id)
                .queryParam("fields","issuetype,priority")
                .filter(session)
                .log().all()
                .when().get("rest/api/2/issue/{key}")
                .then().statusCode(200)
                .log().all();
    }

    // Verifying the comment is available in the issue.
    // suppose n comments are available and want to verify one comment is available inside the comment or not.

    @Test(priority = 4)
    public void checkGivenComment()
    {
        String temp = null;
        String issueDetails= given().pathParam("key",issue_id)
                .filter(session)
                .when().get("rest/api/2/issue/{key}")
                .then()
                .statusCode(200)
                .extract().response().asString();
        JsonPath js=new JsonPath(issueDetails);
        System.out.println(issueDetails);
        String text="After fix getting Error while loading the page";

        int NosOfComments =js.getInt("fields.comment.comments.size()");
        System.out.println("NosOfComments= "+NosOfComments);

        for (int i = 0; i< NosOfComments; i++)
        {

             temp=js.getString("fields.comment.comments["+i+"].body");
            System.out.println(temp);
           if(temp.equalsIgnoreCase("After fix getting Error while loading the page"))
           {
               break;
           }
        }
        System.out.println(temp);
        Assert.assertEquals(text,temp);


    }
}
   
