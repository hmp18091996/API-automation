package files;

public class payload {

	public static String AddPlace() {
		return "{\r\n"
				+ "    \"location\": {\r\n"
				+ "        \"lat\": 38.383494,\r\n"
				+ "        \"lng\": 33.427362\r\n"
				+ "    },\r\n"
				+ "    \"accuracy\": 50,\r\n"
				+ "    \"name\": \"HMP\",\r\n"
				+ "    \"phone_number\": \"(+91) 983 893 3937\",\r\n"
				+ "    \"address\": \"my address\",\r\n"
				+ "    \"types\": [\r\n"
				+ "        \"shoe park\",\r\n"
				+ "        \"shop\"\r\n"
				+ "    ],\r\n"
				+ "    \"website\": \"https://rahulshettyacademy.com\",\r\n"
				+ "    \"language\": \"French-IN\"\r\n"
				+ "}";
	}
	
	public static String CoursePrice() {
		return "{\r\n"
				+ "   \"dashboard\":{\r\n"
				+ "      \"purchaseAmount\":910,\r\n"
				+ "      \"website\":\"rahulshettyacademy.com\"\r\n"
				+ "   },\r\n"
				+ "   \"courses\":[\r\n"
				+ "      {\r\n"
				+ "         \"title\":\"Selenium Python\",\r\n"
				+ "         \"price\":50,\r\n"
				+ "         \"copies\":6\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "         \"title\":\"Cypress\",\r\n"
				+ "         \"price\":40,\r\n"
				+ "         \"copies\":4\r\n"
				+ "      },\r\n"
				+ "      {\r\n"
				+ "         \"title\":\"RPA\",\r\n"
				+ "         \"price\":45,\r\n"
				+ "         \"copies\":10\r\n"
				+ "      }\r\n"
				+ "   ]\r\n"
				+ "}";
	}
	
	public static String Addbook() {
		
		String payload = "{\r\n"
				+ "    \"name\": \"Learn Appium Automation with Java\",\r\n"
				+ "    \"isbn\": \"bcd\",\r\n"
				+ "    \"aisle\": \"29216\",\r\n"
				+ "    \"author\": \"John foer\"\r\n"
				+ "}";
		
		return payload;
	}
	
	public static String Addbook(String isbn, String aisle) {
		
		String payload = "{\r\n"
				+ "    \"name\": \"Learn Appium Automation with Java\",\r\n"
				+ "    \"isbn\": \"" + isbn + "\",\r\n"
				+ "    \"aisle\": \"" + aisle + "\",\r\n"
				+ "    \"author\": \"John foer\"\r\n"
				+ "}";
		
		return payload;
	}
	
	public static String jwtTokenPayload() {
		
		String payload = "{\r\n"
				+ "    \"phone\": \"0931828999\",\r\n"
				+ "    \"deviceID\": \"iphoneHMP01\"\r\n"
				+ "}";
		
		return payload;
	}
	
public static String handshakePayload(String clientPubKey) {
		
		String payload = "{\r\n"
				+ "  \"Language\": \"vi\",\r\n"
				+ "  \"Data\": {\r\n"
				+ "      \"PublicKey\": \"" + clientPubKey + "\"\r\n"
				+ "  },\r\n"
				+ "  \"RequestDateTime\": \"" + ReUsableMethods.currentTime() + "\",\r\n"
				+ "  \"RequestID\": \"" + ReUsableMethods.uuid() + "\"\r\n"
				+ "}";
		
		return payload;
	}
}
