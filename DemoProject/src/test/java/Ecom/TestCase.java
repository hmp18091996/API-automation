package Ecom;

import org.testng.annotations.Test;

import files.ReUsableMethods;

import static io.restassured.RestAssured.given;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import org.testng.annotations.BeforeClass;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.AfterClass;

public class TestCase {

	Login login;
	RequestSpecification reqSpec;
	ResponseSpecification resSpec;

	String token, userId;
	
	@BeforeClass
	public void beforeClass() {

		login = new Login();
		login.setUserEmail("hmp1809@gmail.com");
		login.setUserPassword("HMPhuong1809^^");
		
		reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		resSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
	}

	@Test
	public void TC_01_Login() { 
		
		RequestSpecification request = given().log().all().spec(reqSpec).body(login);
		
		Response resLogin = request.when().post("api/ecom/auth/login")
				.then().log().all().spec(resSpec).extract().response();
		
//		JsonPath js = ReUsableMethods.rawToJson(resLogin.toString());
//		token = js.getString("token");
//		userId = js.getString("userId");
//		
//		assertEquals(token, token);

	}
	
	@Test
	public void TC_02_CreateProduct() { 
		
		RequestSpecification request = given().log().all().spec(reqSpec).body(login);
		
		Response resLogin = request.when().post("api/ecom/auth/login")
				.then().log().all().spec(resSpec).extract().response();
		

	}

	@AfterClass
	public void afterClass() {
	}

}
