package library_api_pratice_files;

public class payload {
    public static String Addbook()
    {
        return "{\n" +
                "\"name\":\"Black Boy\",\n" +
                "\"isbn\":\"1111\",\n" +
                "\"aisle\":\"292611\",\n" +
                "\"author\":\"John foer\"\n" +
                "}\n";
    }

    public static String Addbook(String isbn, int aisle)
    {
        return "{\n" +
                "\"name\":\"Black Boy\",\n" +
                "\"isbn\":\""+isbn+"\",\n" +
                "\"aisle\":\""+aisle+"\",\n" +
                "\"author\":\"John foer\"\n" +
                "}";
    }
}
