import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.Location;
import io.restassured.RestAssured;

public class SerializationTest {

	public static void main(String[] args) {
		
		AddPlace addPlace = new AddPlace();
		Location location = new Location();

		location.setLat(38.383494);
		location.setLng(33.427362);
		
		List<String> types = new ArrayList<String>();
		types.add("shoe park");
		types.add("shoe park");
		
		addPlace.setLocation(location);
		addPlace.setAccuracy(50);
		addPlace.setAccuracy(50);
		addPlace.setName("HMP");
		addPlace.setPhone_number("(+91) 983 893 3937");
		addPlace.setAddress("My Address");
		addPlace.setTypes(types);
		addPlace.setWebsite("https://rahulshettyacademy.com");
		addPlace.setLanguage("French-IN");
		
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all()
				.queryParam("key", "qaclick123")
				.header("Content-Type", "application/json")
				.body(addPlace)
				.when().post("maps/api/place/add/json")
				.then().assertThat().statusCode(200)
				.body("scope", equalTo("APP"))
				.header("server", "Apache/2.4.41 (Ubuntu)")
				.extract().response()
				.asString();
		
	}

}
