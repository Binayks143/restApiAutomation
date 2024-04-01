package OAuth;
import io.restassured.path.json.JsonPath;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

import static io.restassured.RestAssured.given;

public class OAuthTest
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

        //Split will split the delimeter [1] means after delimeter and [0] means before delemeter
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
        String response=given().queryParam("access_token",access_token)
                .when()
                .log().all()
                .get("https://rahulshettyacademy.com/getCourse.php")
                .asString();
        System.out.println(response);

    }

}
