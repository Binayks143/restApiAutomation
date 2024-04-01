package pojo.serialization_test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class SerializeTestUsingPojo {
    public static void main(String[] args)
    {
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


        RestAssured.baseURI="https://rahulshettyacademy.com";
        Response res=given().queryParam("key","qaclick123")
                .log().all()
                .body(p)
                .when()
                .log().all()
                .post("/maps/api/place/add/json")
                .then()
                .statusCode(200).extract().response();
                System.out.println(res);
    }
}