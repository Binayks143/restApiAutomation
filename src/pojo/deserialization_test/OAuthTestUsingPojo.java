package pojo.deserialization_test;

import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;

public class OAuthTestUsingPojo
{
    public static void main(String[] args) throws InterruptedException {
        //Getting the code from browser login using selenium

        // Set the path to the ChromeDriver executable (download and place it in system)
        System.setProperty("webdriver.chrome.driver","C:\\Binay\\Selenium\\Driver\\chromedriver-win64\\chromedriver.exe");
        // Initialize the ChromeDriver
        WebDriver driver= new ChromeDriver();
        driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
        driver.findElement(By.id("identifierId")).sendKeys("binay.kumar@smartcoin.co.in");

        driver.findElement(By.xpath("//span[normalize-space()='Next']")).click();
        Duration timeout = Duration.ofSeconds(10);

        WebDriverWait wait = new WebDriverWait(driver, timeout);

        WebElement passwordField = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//input[@type=\"password\"]")));
        passwordField.sendKeys("B1430ks@");
        driver.findElement(By.xpath("//span[normalize-space()='Next']")).click();
        Thread.sleep(2000);
        String curreltUrl=driver.getCurrentUrl();
        System.out.println("CurrentUrl=" +curreltUrl);
        driver.quit();

        //Split will split the delimiter [1] means after delimiter and [0] means before delimiter
        String partialcurrentUrl=curreltUrl.split("code=")[1];
        String code =partialcurrentUrl.split("&scope")[0];
        System.out.println("code="+code);


        // Methods to get the access token here we need code from web UI
        //urlEncodingEnabled : our response contains special char i.e. % and restassured by default assume this
        //as integer so to disable this we have to use urlEncodingEnabled : false
        String accessTokenResponse = given()
                .urlEncodingEnabled(false)
                .queryParam("code",code)
                .queryParam("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
                .queryParam("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
                .queryParam("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
                .queryParam("grant_type", "authorization_code")
                .when()
                .log().all()  // Log the request details
                .post("https://www.googleapis.com/oauth2/v4/token")
                .asString();  // Store the response as a string

        System.out.println(accessTokenResponse);  // Print the response


        JsonPath js =new JsonPath(accessTokenResponse);
        String access_token=js.getString("access_token");
        System.out.println("access_token="+access_token);


//Actual request here we need access token from previous method
        //.expect().defaultParser(Parser.JSON) this is used to get the default parser
        //if header Content-Type is application/json no need to give this if dev team has given some other
        //response we are converting it into defaultparser like JSON


        // POJO Deserialization : by giving the class name we can achieve this
        GetCourse response=given().queryParam("access_token",access_token)
                .expect().defaultParser(Parser.JSON)
                .when()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .as(GetCourse.class);

// getting the object
        System.out.println("Instructor name is "+response.getInstructor());
        System.out.println("Expertise is "+ response.getExpertise());
// extracting the first web automation course title if index is known
        System.out.println("webautomation 2nd index course : "+response.getCourses().getWebAutomation().get(2).getCourseTitle());
        System.out.println("mobile course title is: "+response.getCourses().getMobile().get(0).getCourseTitle());

// If index is unknown we have use loop concept and find the required courses and its price.
// e.g. get the price of Cypress webautomation
        List<WebAutomation> temp=response.getCourses().getWebAutomation();
        for (int i=0;i<temp.size();i++)
        {
            if (temp.get(i).getCourseTitle().equalsIgnoreCase("Cypress"))
            {
                System.out.println("price of the "+temp.get(i).getCourseTitle()+" webautomation is :"+temp.get(i).getPrice());
            }
        }

//(Q) Get the all courses name available for webautomation
        //Creating a array list
        //When we known the size we will use array if we don't known the size then we use arraylist

        ArrayList<String> actualCourses =new ArrayList<String>();
        List<WebAutomation> wa=response.getCourses().getWebAutomation();
        for (int i=0;i< wa.size();i++)

        {
            actualCourses.add(wa.get(i).getCourseTitle());
        }
        System.out.println("All the courses available for web automation is :"+actualCourses);

//Q) Verify whether actual courses and expected courses is same or not.
        String [] expectedCourses={"Selenium Webdriver Java","Cypress","Protractor"};
        //To compare arraylist and array we have to convert array to array list as follows

        List<String> expectedList= Arrays.asList(expectedCourses);

        Assert.assertTrue(actualCourses.equals(expectedList));

    }

}
