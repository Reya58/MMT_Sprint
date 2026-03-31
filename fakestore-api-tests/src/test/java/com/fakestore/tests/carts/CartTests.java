package com.fakestore.tests.carts;

import com.fakestore.models.Cart;

import com.fakestore.tests.BaseTest;
import com.fakestore.utils.SpecFactory;

import constants.Routes;
import io.qameta.allure.*;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests for /carts endpoints.
 *
 * Covers:
 *  - GET all carts
 *  - GET single cart by ID
 *  - GET carts by user ID
 *  - GET carts with date range
 *  - GET carts with limit & sort
 *  - POST create cart
 *  - PUT update cart
 *  - PATCH partial update
 *  - DELETE cart
 */
@Epic("Shopping Cart")
@Feature("Cart CRUD")
public class CartTests extends BaseTest {

    private static final String CARTS_PATH = "/carts";

    // ------------------------------------------------------------------
    // GET All Carts
    // ------------------------------------------------------------------

    @Test(description = "GET /carts returns 200 and a list of carts")
    @Story("Get all carts")
    public void getAllCarts_shouldReturn200AndNonEmptyList() {
        Response response = 
        		SpecFactory.authSpec()
        		.when()
        		.get(Routes.GET_ALL_CARTS)
        		.then()
        		.statusCode(200)
        		.extract()
        		.response();

        List<Cart> carts = Arrays.asList(response.as(Cart[].class));
        assertThat(carts).isNotEmpty();
        assertThat(carts).allSatisfy(c -> {
            assertThat(c.getId()).isNotNull().isPositive();
            assertThat(c.getId()).isNotNull().isPositive();
            assertThat(c.getProducts()).isNotNull().isNotEmpty();
        });
    }

    // ------------------------------------------------------------------
    // GET Single Cart
    // ------------------------------------------------------------------

    @Test(description = "GET /carts/{id} returns the correct cart")
    @Story("Get cart by ID")
    @Severity(SeverityLevel.CRITICAL)
    public void getCartById_shouldReturnCorrectCart() {
        int targetId = 1;
        Cart cart = SpecFactory.authSpec()
                .pathParam("id", targetId)
                .when()
                .get(Routes.GET_A_SINGLE_CART)
                .then()
                .statusCode(200)
                .extract().as(Cart.class);

        assertThat(cart.getId()).isEqualTo(targetId);
        assertThat(cart.getProducts()).isNotEmpty();
    }

    // ------------------------------------------------------------------
    // POST – Create
    // ------------------------------------------------------------------

    @Test(description = "POST /carts creates a new cart and returns an ID")
    @Story("Create cart")
    public void createCart_shouldReturnNewId() {
        String body = """
                {
                  "userId": 5,
                  "date": "2024-02-03",
                  "products": [
                    {"productId": 1, "quantity": 3},
                    {"productId": 2, "quantity": 1}
                  ]
                }
                """;

        Cart created = SpecFactory.authSpec()
                .body(body)
                .when()
                .post(Routes.ADD_A_NEW_CART)
                .then()
                .statusCode(200)
                .extract().as(Cart.class);

        assertThat(created.getId()).isNotNull().isPositive();
    }

    // ------------------------------------------------------------------
    // DELETE
    // ------------------------------------------------------------------

    @Test(description = "DELETE /carts/{id} returns 200 with the deleted cart")
    @Story("Delete cart")
    @Severity(SeverityLevel.NORMAL)
    public void deleteCart_shouldReturn200() {
        Cart deleted = SpecFactory.authSpec()
                .pathParam("id", 6)
                .when()
                .delete(CARTS_PATH + "/{id}")
                .then()
                .statusCode(200)
                .extract().as(Cart.class);

        assertThat(deleted.getId()).isEqualTo(6);
    }
}