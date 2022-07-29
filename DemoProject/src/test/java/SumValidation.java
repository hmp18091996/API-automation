
import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payload;
import io.restassured.path.json.JsonPath;

public class SumValidation {

	@Test
	public void sumOfCourses() {
		JsonPath js = ReUsableMethods.rawToJson(payload.CoursePrice());

		// Print all courses titles and their respective Prices
		int count = js.getInt("courses.size()");
		int sum = 0;

		for (int i = 0; i < count; i++) {
			int priceAllCourse = js.getInt("courses[" + i + "].price");
			int copiesAllCourse = js.getInt("courses[" + i + "].copies");
			int amount = priceAllCourse * copiesAllCourse;
			System.out.println(amount);
			sum = sum + amount;
			System.out.println("Total: " + sum);
		}

		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		assertEquals(sum, purchaseAmount);

	}

}
