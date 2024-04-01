// Validating the response in a body/output
// To validate the output we have to add equalTo method which is coming from hamcrest library.
package binay_pratice;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;


public class b02_Basics_Add_Place_02 {

    public static void main(String[] args) {


        RestAssured.baseURI="https://rahulshettyacademy.com";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
                .body("{\r\n"
                        + "  \"location\": {\r\n"
                        + "    \"lat\": -30.383494,\r\n"
                        + "    \"lng\": 30.427362\r\n"
                        + "  },\r\n"
                        + "  \"accuracy\": 70,\r\n"
                        + "  \"name\": \"Frontline house\",\r\n"
                        + "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
                        + "  \"address\": \"29, side layout, cohen 09\",\r\n"
                        + "  \"types\": [\r\n"
                        + "    \"shoe park\",\r\n"
                        + "    \"shop\"\r\n"
                        + "  ],\r\n"
                        + "  \"website\": \"http://google.com\",\r\n"
                        + "  \"language\": \"French-IN\"\r\n"
                        + "}\r\n"
                        + "").when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).
                body("scope",equalTo("APP")).
                header("Server","Apache/2.4.52 (Ubuntu)");



    }

}
