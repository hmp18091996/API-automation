package HCE;

import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class JwtToken {
	
	String be_jwt;
	String clientPubKey;
	String jwt;
	
	String sessionKey, cipherSessionKey, salt, publicKey, iv, beSessionKey, hsObject;
	
	
	@Test (priority = 1)
	public void getTokenDeviceID() {

		RestAssured.baseURI = "http://103.161.38.176:8089";
		String response = given().header("Content-Type","application/json")
		.body(payload.jwtTokenPayload())
		.when()
		.post("/device/login")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		be_jwt = js.get("token");	
	}
	
	@Test (priority = 2)
	public void testPublicKey() {

		RestAssured.baseURI = "http://103.161.38.176:8089";
		String response = given().header("Content-Type","application/json")
		.body(payload.jwtTokenPayload())
		.when()
		.get("/test/publicKey")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		clientPubKey = js.get("publicKey");	
	}
	
	@Test  (priority = 3)
	public void handshake() {

		//System.err.println(payload.handshakePayload(clientPubKey).toString());
		
		RestAssured.baseURI = "http://103.161.38.176:8008";
		String response = given().header("Content-Type","application/json")
		.header("Authorization","Bearer " + be_jwt)
		.body(payload.handshakePayload(clientPubKey))
		.when()
		.post("/auth/handshake")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
	
		sessionKey = js.get("RequestID");
		cipherSessionKey = js.get("Data.SessionKey");
		salt = js.get("Data.Salt");
		publicKey = js.get("Data.PublicKey");
		iv = js.get("Data.IV");
		beSessionKey = js.get("Data.SessionKey");
		
		hsObject = js.getJsonObject("Data").toString();
		
		System.err.println(hsObject);
		
	}
	
	@Test (priority = 4)
	public void testDecrypt() {
		
		RestAssured.baseURI = "http://103.161.38.176:8089";
		String response = given().log().all().header("Content-Type","application/json")	
		.body(hsObject)
		.when()
		.post("/test/decrypt")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		jwt = js.get("JWT");
		
		System.out.println("=========== JWT ===========");
		System.out.println(jwt);
	}
}
