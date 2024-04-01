package binay_pratice;

import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static io.restassured.RestAssured.given;

public class Demo {
    public static void main(String[] args) throws IOException {
        String a = new String(Files.readAllBytes(Paths.get("./src/files/jcomplex.json")));
        org.json.JSONObject js1= new JSONObject(a);
        js1.getJSONArray("courses").getJSONObject(0).put("title","sele");
        System.out.println(js1);



    }
}
