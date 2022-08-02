package files;

public class payloadForJira {
	
	public static String getTokenPayload() {
		
		String payload = "{\n"
				+ "    \"username\": \"hmp.18091996\",\n"
				+ "    \"password\": \"hmphuong1809\"\n"
				+ "}";
		
		return payload;
	}
	
	public static String createIssue() {
		
		String payload = "{\n"
				+ "    \"fields\": {\n"
				+ "        \"project\": {\n"
				+ "            \"key\": \"AAPI\"\n"
				+ "        },\n"
				+ "        \"summary\": \"Postman send api\",\n"
				+ "        \"description\": \"create bug issue\",\n"
				+ "        \"issuetype\": {\n"
				+ "            \"name\": \"Bug\"\n"
				+ "        }\n"
				+ "    }\n"
				+ "}";
		
		return payload;
	}
	
	public static String createIssue(String projectKey, String summary, String desciption,String issueType) {
		
		String payload = "{\n"
				+ "    \"fields\": {\n"
				+ "        \"project\": {\n"
				+ "            \"key\": \"" + projectKey + "\"\n"
				+ "        },\n"
				+ "        \"summary\": \"" + summary + "\",\n"
				+ "        \"description\": \"" + desciption + "\",\n"
				+ "        \"issuetype\": {\n"
				+ "            \"name\": \"" + issueType + "\"\n"
				+ "        }\n"
				+ "    }\n"
				+ "}";
		
		return payload;
	}
	
	public static String addCommentPayload() {
		
		String payload = "{\n"
				+ "    \"body\": \"I have commented for REST API\",\n"
				+ "    \"visibility\": {\n"
				+ "        \"type\": \"role\",\n"
				+ "        \"value\": \"Administrators\"\n"
				+ "    }\n"
				+ "}";
		
		return payload;
	}
	
	public static String addCommentPayload(String commentBody) {
		
		String payload = "{\n"
				+ "    \"body\": \"" + commentBody + "\",\n"
				+ "    \"visibility\": {\n"
				+ "        \"type\": \"role\",\n"
				+ "        \"value\": \"Administrators\"\n"
				+ "    }\n"
				+ "}";
		
		return payload;
	}
	
	public static String addAttachmentsPayload() {
		
		String payload = "";
		
		return payload;
	}
	
}
