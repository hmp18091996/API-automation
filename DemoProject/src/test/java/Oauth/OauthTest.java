package Oauth;

import static io.restassured.RestAssured.given;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.path.json.JsonPath;

public class OauthTest {

	public static void main(String[] args) throws InterruptedException {
		
		
//		WebDriverManager.chromedriver().setup();
//		WebDriver driver = new ChromeDriver();
//		
//		driver.get("https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php");
//		
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys("hmp.18091996@gmail.com");
//		driver.findElement(By.cssSelector("input[type='email']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys("hmphuong1809");
//		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(Keys.ENTER);
//		Thread.sleep(3000);
//		
//		String url = driver.getCurrentUrl();
//		System.out.println(url);
		
		String url = "https://rahulshettyacademy.com/getCourse.php?code=4%2F0AdQt8qhMnsrhSjLXgkFet204oHYOKdiR6glfjHhEbs0mOt-mHpbD3CSTPhdqK8IwOKjEMw&scope=email+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email+openid&authuser=0&prompt=consent";
		String parialCode = url.split("code=")[1];
		String code = parialCode.split("&scope")[0];
		
		System.out.println(code);
		
		String accessTokenResponse = given()
		.queryParams("code",code)
		.queryParams("client_id","692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
		.queryParams("client_secret","erZOWM9g3UtwNRj340YYaK_W")
		.queryParams("redirect_uri","https://rahulshettyacademy.com/getCourse.php")
		.queryParams("grant_type","authorization_code")
		.when().log().all()
		.post("https://www.googleapis.com/oauth2/v4/token").asString();
		
		JsonPath js = new JsonPath(accessTokenResponse);
		
		String accessToken = js.getString("access_token");
		System.out.println(accessToken);
		
		String resonpse = given().queryParam("access_token", accessToken)
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(resonpse);
		
	}
	
}
