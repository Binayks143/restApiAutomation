// Storing the response in a variable
// picking the key's value from response variable

package binay_pratice;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import files.payload;
import io.restassured.path.json.JsonPath;


public class b04_ExtractKeyFromJson {

    public static void main(String[] args) {


        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response=given().queryParam("key", "qaclick123").header("Content-Type","application/json")
                .body(payload.AddPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).
                body("scope",equalTo("APP")).
                header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println("response=" + response);

        JsonPath js=new JsonPath(response); // for parsing JSON
        String placeId=js.getString("place_id");
        System.out.println("placeId=  "+placeId);



    }

}
