
import files.ReUsableMethods;
import files.payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {

	public static void main(String[] args) {

		JsonPath js = ReUsableMethods.rawToJson(payload.CoursePrice());

		// Print No of courses returned by API
		int count = js.getInt("courses.size()");
		System.out.println(count);

		// Print Purchase Amount
		int totalAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println(totalAmount);

		// Print Title of first coursee
		String titleFirstCourse = js.get("courses[0].title");
		System.out.println(titleFirstCourse);

		// Print all courses titles and their respective Prices
		System.out.println("-----------------------------------");
		for (int i = 0; i < count; i++) {
			String titleAllCourse = js.get("courses[" + i + "].title");
			int priceAllCourse = js.getInt("courses[" + i + "].price");
			int copiesAllCourse = js.getInt("courses[" + i + "].copies");
			System.out.println(titleAllCourse);
			System.out.println(priceAllCourse);
			System.out.println(copiesAllCourse);
		}

		// Print no of copies sold by RPA Course
		System.out.println("-----------------------------------");
		for (int i = 0; i < count; i++) {
			String titleAllCourse = js.get("courses[" + i + "].title");
			if (titleAllCourse.contains("RPA")) {
				int copiesAllCourse = js.getInt("courses[" + i + "].copies");
				System.out.println(copiesAllCourse);
				break;
			}
		}
	}
}
