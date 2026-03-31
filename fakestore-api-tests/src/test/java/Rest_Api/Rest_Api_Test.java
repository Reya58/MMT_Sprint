package Rest_Api;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fakestore.models.User;

import io.restassured.response.Response;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import org.testng.Assert;

public class Rest_Api_Test extends Base_Api {

	@BeforeMethod
	public void init() {
		setup();
	}

	@Test
	public void testGetUser() {

		Response response = request.when().get("/users/1");

		response.then().statusCode(200);
	}

	@Test
	public void getSingleUser() {

		Response response = request.log().all().when().get("/users/1"); // change ID if needed

		// Validate status code
		response.then().log().all().statusCode(200);

		// Extract and validate data
		int id = response.jsonPath().getInt("id");
		String username = response.jsonPath().getString("username");

		System.out.println("User ID: " + id);
		System.out.println("Username: " + username);

		// Assertions

		Assert.assertEquals(id, 1);
		Assert.assertNotNull(username);
	}

	@Test
	public void updateUser() {

		int userId = 1; // user to update

		User user = new User();
		user.setId(1);
		user.setUsername("derek_updated");
		user.setEmail("derek_updated@test.com");
		user.setPassword("jklg*_56");

		Response response = request.pathParam("id", userId).body(user).log().all().when().put("/users/{id}");

		// Validate response
		response.then().log().all().statusCode(200); // sometimes 200 or 204

		// Validate updated fields
		String username = response.jsonPath().getString("username");

		System.out.println("Updated Username: " + username);

		assertEquals(username, "derek_updated");
	}

	@Test
	public void deleteUser() {

		int userId = 1; // user to delete

		Response response = request.pathParam("id", userId).log().all().when().delete("/users/{id}");

		// Validate response
		response.then().log().all().statusCode(200);

	}
}