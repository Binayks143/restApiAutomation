package library_api_pratice;

import files.ReUseAblemethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import library_api_pratice_files.payload;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;


public class b01_DynamicJson {
    @Test
    public void addBook() {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String temp = given().log().all().header("Content-Type", "application/json")
                .body(payload.Addbook("T12344", 554455)).when().log().all().post("Library/Addbook.php").
                then()
                .assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUseAblemethods.rowToJson(temp);
        String id = js.getString("ID");
        System.out.println("Added Book id= " + id);

    }


    @Test
    public void deleteBook()
    {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String temp = given().log().all().header("Content-Type", "application/json")
                .body(payload.Addbook("T12344", 554455)).when().log().all().post("Library/Addbook.php").
                then()
                .assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUseAblemethods.rowToJson(temp);
        String id = js.getString("ID");
        System.out.println("Added Book id= " + id);

        given().log().all().header("Content-Type","application/json").
                body("{\n" +
                "    \"ID\": \""+id+"\"\n" +
                "}").when().log().all().delete("https://rahulshettyacademy.com/Library/DeleteBook.php")
                .then().assertThat().statusCode(200);
    }


}
