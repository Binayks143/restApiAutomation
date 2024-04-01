package library_api_pratice;

import files.ReUseAblemethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class b03_External_Payload {
    @Test
    public void add_book_using_extenal_file() throws IOException
    // Flow: Content of the file to String --> Content of the file can convert into Byte --> Byte to string
    {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String temp = given().log().all().header("Content-Type", "application/json")
                .body(new String(Files.readAllBytes(Paths.get("C:\\Binay\\RestAssuredAPI\\BookPayloadFile.json"))))
                .when().log().all().post("Library/Addbook.php").
                then().assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUseAblemethods.rowToJson(temp);
        String id = js.getString("ID");
        System.out.println("Added Book id= " + id);
    }

}

