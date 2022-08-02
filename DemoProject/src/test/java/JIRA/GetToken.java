package JIRA;

import static io.restassured.RestAssured.given;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import files.payloadForJira;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;


public class GetToken {
	
	
	String baseUrl = "http://localhost:8080/";
	String tokenName, tokenValue;
	String cookie;
	String issueId, issueKey, issueSaft;
	String commentId;
	String attachmentId;
	
	// Create issue res
	String projectKey, summary, desciption, issueType;
	
	// Create comment res
	String comment;
	
	SessionFilter session;
	
	
	
	@Test (priority = 1)
	public void getTokenDeviceID() {

		session = new SessionFilter();
		
		RestAssured.baseURI = baseUrl;
		String response = given().relaxedHTTPSValidation()
		.header("Content-Type","application/json")
		.body(payloadForJira.getTokenPayload())
		.log().all().filter(session)
		.when()
		.post("/rest/auth/1/session")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		
		tokenName = js.getString("session.name");
		tokenValue = js.getString("session.value");
		
		cookie = tokenName + "=" + tokenValue;
		
		System.out.println(cookie);
		System.out.println("------------------------------------------------------");
	}
	
	@Test (priority = 2)
	public void createIssue() {

		projectKey = "AAPI";
		summary = "Create issue in java";
		desciption = "Automation create issue";
		issueType = "Bug";
		
		RestAssured.baseURI = baseUrl;
		String response = given().header("Content-Type","application/json")
		//.header("cookie",cookie)
		.body(payloadForJira.createIssue(projectKey, summary, desciption, issueType))
		.log().all().filter(session)
		.when()
		.post("/rest/api/2/issue")
		.then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		
		issueId = js.getString("id");
		issueKey = js.getString("key");
		issueSaft = js.getString("saft");
		
		System.out.println("------------------------------------------------------");
	}
	
	@Test (priority = 3)
	public void addComment() {

		comment = "Add comment in java";
		
		RestAssured.baseURI = baseUrl;
		String response = given().header("Content-Type","application/json")
		//.header("cookie",cookie)
		.pathParam("key", issueKey)
		.body(payloadForJira.addCommentPayload(comment))
		.log().all().filter(session)
		.when()
		.post("rest/api/2/issue/{key}/comment")
		.then().log().all().assertThat().statusCode(201)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		
		commentId = js.getString("id");
		System.out.println("------------------------------------------------------");
	}
	
	@Test (priority = 4)
	public void addAttachment() {

		
		RestAssured.baseURI = baseUrl;
		String response = given().header("X-Atlassian-Token","no-check")
		.header("Content-Type","multipart/form-data")
		.pathParam("key", issueKey)
		.multiPart("file", new File("./src/test/resources/attachmentsFile.txt"))
		.log().all().filter(session)
		.when()
		.post("rest/api/2/issue/{key}/attachments")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		
		attachmentId = js.getString("id");
		
		System.out.println("------------------------------------------------------");
	}
	
	
	@Test (priority = 5)
	public void getIssue() {

		if(issueKey == null) {
			issueKey = "AAPI-12";
		}
		
		if(cookie == null) {
			cookie = "JSESSIONID=9204F7DA775CE663A1A9043B2EDD4664";
		}
		
		if(commentId == null) {
			commentId = "10107";
		}
		
		if(comment == null) {
			comment = "Add comment in java";
		}
		
		RestAssured.baseURI = baseUrl;
		String response = given().header("Content-Type","application/json")
		.header("Cookie",cookie)
		.param("fields", "comment") // Get list comment
		.pathParam("key", issueKey)
//		.log().all().filter(session)
		.when()
		.get("/rest/api/2/issue/{key}")
		.then().log().all().assertThat().statusCode(200)
		.extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(response);
		
		int countComment = js.getInt("fields.comment.comments.size()");
		String commentActual = "";
		
		for(int i = 0; i < countComment; i ++) {
			if(js.getString("fields.comment.comments[" + i + "].id").equals(commentId)) {
				commentActual = js.getString("fields.comment.comments[" + i + "].body");
				break;
			}
		}
		
		Assert.assertEquals(commentActual, comment); 
		System.out.println("------------------------------------------------------");
	}
	
	
}
