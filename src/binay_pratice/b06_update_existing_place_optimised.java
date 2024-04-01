// There row to JSON has been imported from reuseable class



package binay_pratice;

import files.ReUseAblemethods;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.equalTo;
import files.payload;
import io.restassured.path.json.JsonPath;
import org.testng.Assert;


public class b06_update_existing_place_optimised {

    public static void main(String[] args) {


        RestAssured.baseURI="https://rahulshettyacademy.com";
        String response=given().queryParam("key", "qaclick123").header("Content-Type","application/json")
                .body(payload.AddPlace()).when().post("maps/api/place/add/json")
                .then().log().all().assertThat().statusCode(200).
                body("scope",equalTo("APP")).
                header("Server","Apache/2.4.52 (Ubuntu)").extract().response().asString();

        System.out.println("response=" + response);

        JsonPath js=ReUseAblemethods.rowToJson(response);
        String placeId=js.getString("place_id");
        System.out.println("placeId=  "+placeId);

        // Updating the address
        String newAddress="BANGALORE";
        given().log().all().queryParam("key","qaclick123")
                .header("Content-Type","application/json")
                .body("{\n" +
                        "\"place_id\":\""+placeId+"\",\n" +
                        "\"address\":\""+newAddress+"\",\n" +
                        "\"key\":\"qaclick123\"\n" +
                        "}\n").when().put("maps/api/place/update/json")
                .then().log().all().assertThat().statusCode(200)
                .body("msg",equalTo("Address successfully updated"));

        //Verifying the update
        String update_response=given().log().all().queryParam("key","qaclick123")
                .queryParam("place_id",placeId).when().get("maps/api/place/get/json")
                .then().log().all().statusCode(200).body("address",equalTo("BANGALORE")).extract().response().asString();
        JsonPath js1=ReUseAblemethods.rowToJson(update_response);
        String address=js1.getString("address" );
        System.out.println("address=  "+address);
        Assert.assertEquals(address,newAddress);



    }

}
