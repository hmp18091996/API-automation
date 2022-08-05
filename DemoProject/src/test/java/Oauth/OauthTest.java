package Oauth;

import static io.restassured.RestAssured.given;

public class OauthTest {

	public static void main(String[] args) {
		
		
//		given()
//		.queryParams("code","")
//		.queryParams("client_id","")
//		.queryParams("","")
//		.queryParams("","")
//		.queryParams("","")
		
		
		String resonpse = given().queryParam("access_token", "")
		.when()
		.get("https://rahulshettyacademy.com/getCourse.php").asString();
		
		System.out.println(resonpse);
		
	}
	
}
