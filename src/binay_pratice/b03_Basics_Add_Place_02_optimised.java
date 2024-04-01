// Providing the payload from files packages inside the body
package binay_pratice;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import files.payload;


public class b03_Basics_Add_Place_02_optimised {

    public static void main(String[] args) {


        RestAssured.baseURI="https://rahulshettyacademy.com";
        given().log().all().queryParam("key", "qaclick123").header("Content-Type","application/json")
                .body(payload.AddPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).
                body("scope",equalTo("APP")).
                header("Server","Apache/2.4.52 (Ubuntu)");



    }

}
