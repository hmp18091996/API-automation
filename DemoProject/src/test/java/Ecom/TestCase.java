package Ecom;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TestCase {

	LoginRequest login;
	RequestSpecification reqSpec, reqSpecCreateProduct;
	ResponseSpecification resSpec;

	String token, userId;
	
	@BeforeClass
	public void beforeClass() {

		login = new LoginRequest();
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
		
		LoginResponse resLogin = request.when().post("api/ecom/auth/login")
				.then().log().all().spec(resSpec).extract().response().as(LoginResponse.class);
		
		token = resLogin.getToken(); // Get Authorization
		userId = resLogin.getUserId(); 
	}
	
	@Test
	public void TC_02_CreateProduct() { 
		
		reqSpecCreateProduct = new RequestSpecBuilder().addHeader("Authorization", token).build();
		
//		RequestSpecification request = given().log().all().spec(reqSpecCreateProduct)
//				.param("productName", "Proruct")
//				.param("productAddedBy", userId)
//				.param("productCategory", "shirts")
//				.param("productSubCategory", "shirts")
//				.param("productPrice", 11500)
//				.param("productDescription", "Addias Originals")
//				.param("productFor", "women")
//				.multiPart("productImage", new File());
		
		

	}

	@AfterClass
	public void afterClass() {
	}

}
