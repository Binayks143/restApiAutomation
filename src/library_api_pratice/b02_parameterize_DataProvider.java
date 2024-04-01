/*
Parameterize the API test with multiple dataset (multiple input)
array=collection of elements
multidimensional array= collection of arrays
*/

package library_api_pratice;

import files.ReUseAblemethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import library_api_pratice_files.payload;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class b02_parameterize_DataProvider {

    @Test(dataProvider="MultiData")
    public void add_book(String isbn, int aisle)

    {
        RestAssured.baseURI = "https://rahulshettyacademy.com";
        String temp = given().log().all().header("Content-Type", "application/json")
                .body(payload.Addbook(isbn, aisle)).when().log().all().post("Library/Addbook.php").
                then()
                .assertThat().statusCode(200).extract().response().asString();

        JsonPath js = ReUseAblemethods.rowToJson(temp);
        String id = js.getString("ID");
        System.out.println("Added Book id= " + id);

    }

    // Once we are returning return type should change if we are writing void we are not returning anything
    // here we are returning multidimensional array i.e. object
    // We can give name to this data provider so that we can tie up this with test
    //array=collection of elements
    //multidimensional array= collection of arrays

    @DataProvider(name="MultiData")
    public Object[][] getData()
    {
    return new Object[][] {{"book1",788686965},{"book2",434232},{"book3",445325}};
    }
}



