import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.testng.Assert;

import files.ReUsableMethods;
import files.payload;

public class Basic {

	public static void main(String[] args) throws IOException {
		// Given - all input details
		// When - submit the API
		// Then - validate the response
		
		//content if the file to String -> contents of file convert into Byte -> Byte data to String
		
		String workingDir = System.getProperty("user.dir");
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get(workingDir + "/src/test/resources/addPlace.json")))).when().post("maps/api/place/add/json").then().assertThat().statusCode(200)
				.body("scope", equalTo("APP")).header("server", "Apache/2.4.41 (Ubuntu)").extract().response()
				.asString();

		// System.out.println(response);
		// Add place -> Updaate Place with new address
		JsonPath js = ReUsableMethods.rawToJson(response);
		String placeId = js.get("place_id");

		System.out.println(placeId);

		// Update Place
		String newAddress = "my address update";
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body("{\r\n" + "    \"place_id\": \"" + placeId + "\",\r\n" + "    \"address\": \"" + newAddress
						+ "\",\r\n" + "    \"key\": \"qaclick123\"\r\n" + "}")
				.when().put("maps/api/place/update/json").then().assertThat().log().all().statusCode(200)
				.body("msg", equalTo("Address successfully updated"));

		// Get Place

		String getPaleceResponse = given().log().all().queryParam("key", "qaclick123").queryParam("place_id", placeId)
				.when().get("maps/api/place/get/json").then().assertThat().log().all().statusCode(200).extract()
				.response().asString();

		JsonPath js1 = ReUsableMethods.rawToJson(getPaleceResponse);
		String actualAddress = js1.get("address");
		System.out.println(actualAddress);
		Assert.assertEquals(actualAddress, newAddress);

		// Cucumer Junit, TestNG
	}
}
