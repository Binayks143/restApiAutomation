package specBuilderTest;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.serialization_test.AddPlace;
import pojo.serialization_test.Location;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.baseURI;
import static io.restassured.RestAssured.given;

public class RequestSpecBuilder_Test {
    public static void main(String[] args){
        RestAssured.baseURI="https://rahulshettyacademy.com";

        //Doing serialization
        AddPlace p=new AddPlace();
        p.setAccuracy(60);
        p.setName("Frontline house");
        p.setPhone_number("(+91) 983 893 3937");
        p.setAddress("29, side layout, cohen 09");
        p.setWebsite("http://google.com");
        p.setLanguage( "French-IN");

        //types contains list of data so create a object for list class and pass this object as argument to setTypes
        List<String> myList=new ArrayList<String>();
        myList.add("shoe park");
        myList.add("shop");
        p.setTypes(myList);

        // In location we are accepting another class object so create a location class object and access the location values

        Location l=new Location();
        l.setLat(-30.383494);
        l.setLng( 30.427362);
        p.setLocation(l);

        //Creating request spec builder class for reuseablity (all reuseability generic method will be added here)
        // return type must be RequestSpecification for spec

        //Request Specification
        RequestSpecification req=new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
                .addQueryParam("key","qaclick123")
                .setContentType(ContentType.JSON)
                .build();

        //response specification
        ResponseSpecification res=new ResponseSpecBuilder().expectStatusCode(200)
                        .expectContentType(ContentType.JSON).build();

//use spec(<object>)
        Response response=given().spec(req).body(p)
                .when().post("maps/api/place/add/json")
                .then().spec(res).extract().response();
        String responseString=response.asString();
        System.out.println(responseString);

    }
}
