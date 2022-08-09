import static io.restassured.RestAssured.given;

import java.util.ArrayList;
import java.util.List;

import POJO.AddPlace;
import POJO.Location;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilderTest {

	public static void main(String[] args) {

		AddPlace addPlace = new AddPlace();
		Location location = new Location();

		location.setLat(38.383494);
		location.setLng(33.427362);

		List<String> types = new ArrayList<String>();
		types.add("shoe park 1");
		types.add("shoe park 2");

		addPlace.setLocation(location);
		addPlace.setAccuracy(50);
		addPlace.setAccuracy(50);
		addPlace.setName("HMP");
		addPlace.setPhone_number("(+91) 983 893 3937");
		addPlace.setAddress("My Address");
		addPlace.setTypes(types);
		addPlace.setWebsite("https://rahulshettyacademy.com");
		addPlace.setLanguage("French-IN");

		RequestSpecification reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123")
				.setContentType(ContentType.JSON).build();
		
		ResponseSpecification resSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();

		RequestSpecification res = given().log().all().spec(reqSpec).body(addPlace);

		Response response = res.when().post("/maps/api/place/add/json")
				.then().spec(resSpec).log().all()
				.extract().response();
		System.out.println("------");

		String responseString = response.toString();
		System.out.println(responseString);

//		JsonPath js = ReUsableMethods.rawToJson(res.toString());
//		System.out.println(js.getString("id"));

	}

}
