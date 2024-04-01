package pojo.serialization_test;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

//using google maps Add place API
public class SerializeTest {
    public static void main(String[] args)
    {
        RestAssured.baseURI="https://rahulshettyacademy.com";
        Response res =given().queryParam("key","qaclick123")
                .body("{\n" +
                        "  \"location\": {\n" +
                        "    \"lat\": -30.383494,\n" +
                        "    \"lng\": 30.427362\n" +
                        "  },\n" +
                        "  \"accuracy\":60 ,\n" +
                        "  \"name\": \"Frontline house\",\n" +
                        "  \"phone_number\": \"(+91) 983 893 3937\",\n" +
                        "  \"address\": \"29, side layout, cohen 09\",\n" +
                        "  \"types\": [\n" +
                        "    \"shoe park\",\n" +
                        "    \"shop\"\n" +
                        "  ],\n" +
                        "  \"website\": \"http://google.com\",\n" +
                        "  \"language\": \"French-IN\"\n" +
                        "}\n")
                .when()
                .log().all()
                .post("/maps/api/place/add/json")
                .then()
                .statusCode(200)
                .extract().response();
        System.out.println(res);

        String responseString=res.asString();
        System.out.println("responseString="+responseString);
    }
}
