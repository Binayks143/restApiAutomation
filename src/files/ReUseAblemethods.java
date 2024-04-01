package files;

import io.restassured.path.json.JsonPath;

public class ReUseAblemethods {

    public static JsonPath rowToJson(String response)
    {
        JsonPath js=new JsonPath(response);
        return js;
    }
}
