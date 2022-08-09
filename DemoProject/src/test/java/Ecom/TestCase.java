package Ecom;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import files.ReUsableMethods;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class TestCase {

	LoginRequest login;
	OrderDetailsRequest orderDetail;
	OrdersRequest orders;
	RequestSpecification reqSpec, reqSpecCreateProduct, 
						 reqSpecCreateOrder, reqSpecViewCreateOrder, reqSpecDeleteOrder, reqSpecDeleteProduct;
	ResponseSpecification resSpec;

	String token, userId;
	String productId;
	
	List<String> orderIdList, productOderIdList;
	String orderId, productOderId;
	
	
	@BeforeClass
	public void beforeClass() {

		reqSpec = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		
		resSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		
	}

	@Test
	public void TC_01_Login() { 
		
		login = new LoginRequest();
		login.setUserEmail("hmp1809@gmail.com");
		login.setUserPassword("HMPhuong1809^^");
		
		RequestSpecification request = given().log().all().spec(reqSpec).body(login);
		
		LoginResponse resLogin = request.when().post("api/ecom/auth/login")
				.then().log().all().spec(resSpec).extract().response().as(LoginResponse.class);
		
		token = resLogin.getToken(); // Get Authorization
		userId = resLogin.getUserId(); 
	}
	
	@Test
	public void TC_02_CreateProduct() { 
		
		reqSpecCreateProduct = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification request = given().log().all().spec(reqSpecCreateProduct)
				.param("productName", "Proruct")
				.param("productAddedBy", userId)
				.param("productCategory", "shirts")
				.param("productSubCategory", "shirts")
				.param("productPrice", 11500)
				.param("productDescription", "Addias Originals")
				.param("productFor", "women")
				.multiPart("productImage", new File(ReUsableMethods.USER_DIR + "/src/test/resources/Images/image_02.png"));
		
		String responseAddProduct = request.when().post("/api/ecom/product/add-product")
					.then().log().all().statusCode(201).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(responseAddProduct);
		
		productId = js.getString("productId");
		
	}
	
	@Test
	public void TC_03_CreateOrder() { 
		
		List<OrderDetailsRequest> orderDetailList = new ArrayList<OrderDetailsRequest>();
		
		orderDetail = new OrderDetailsRequest();
		orderDetail.setContry("Slovenia");
		orderDetail.setProductOrderedId(productId);
		
		orderDetailList.add(orderDetail);
		
		orders = new OrdersRequest();
		orders.setOrders(orderDetailList); 
		
		reqSpecCreateOrder = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON)
				.addHeader("Authorization", token).build();
		
		RequestSpecification request = given().log().all().spec(reqSpecCreateOrder).body(orders);
		
		String responseAddOrder = request.when().post("api/ecom/order/create-order")
		.then().log().all().statusCode(201).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(responseAddOrder);
		
		
		orderIdList = js.getList("orders");
		productOderIdList = js.getList("productOrderId");
	}

	@Test
	public void TC_04_ViewDetailOrder() { 
		
		reqSpecViewCreateOrder = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
//				.addHeader("Authorization", "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2MmYwZDY2NGUyNmI3ZTFhMTBmNTYzY2IiLCJ1c2VyRW1haWwiOiJobXAxODA5QGdtYWlsLmNvbSIsInVzZXJNb2JpbGUiOjk5OTk5OTk5OTksInVzZXJSb2xlIjoiY3VzdG9tZXIiLCJpYXQiOjE2NjAwNTI4NzAsImV4cCI6MTY5MTYxMDQ3MH0.vgpEDUZn_oAJloYPCdwVOhg8Evr0F42gMeRRN2BrnBU").build();
		
		RequestSpecification request = given().log().all().spec(reqSpecViewCreateOrder).param("id", orderIdList.get(0));
		
		String responseAddOrder = request.when().get("/api/ecom/order/get-orders-details")
				.then().log().all().spec(resSpec).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(responseAddOrder);
		
		assertEquals(js.getString("message"), "Orders fetched for customer Successfully");
		
	}
	
	@Test
	public void TC_05_DeleteOrder() { 
		
		reqSpecDeleteOrder = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification request = given().log().all().spec(reqSpecDeleteOrder).pathParam("orderId", orderIdList.get(0));
		
		String responseDeleteOrder = request.when().delete("/api/ecom/order/delete-order/{orderId}")
				.then().log().all().spec(resSpec).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(responseDeleteOrder);
		
		assertEquals(js.getString("message"), "Orders Deleted Successfully");
		
	}
	
	@Test
	public void TC_06_DeleteProduct() { 
		
		reqSpecDeleteProduct = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		
		RequestSpecification request = given().log().all().spec(reqSpecDeleteProduct).pathParam("productId", productId);
		
		String responseDeleteProduct = request.when().delete("/api/ecom/product/delete-product/{productId}")
				.then().log().all().spec(resSpec).extract().response().asString();
		
		JsonPath js = ReUsableMethods.rawToJson(responseDeleteProduct);
		
		assertEquals(js.getString("message"), "Product Deleted Successfully");
		
	}
	
	@AfterClass
	public void afterClass() {
	}

}
