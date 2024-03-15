package api.cart;

import model.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class CartTests {

    private final static String USERNAME = "nastiya";
    private final static String PASSWORD = "12345";

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268";
        register(USERNAME, PASSWORD);
    }

    @Test
    public void getUserShoppingCart() {

        String token = authorize(USERNAME, PASSWORD);

        given().when().log().all()
                .contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + token))
                .get("/cart")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void addProductToUserShoppingCart() {

        String token = authorize(USERNAME, PASSWORD);

        String product = "{" +
                "\"product_id\": 1," +
                "\"quantity\": 2" +
                "}";

        given().when().log().all()
                .contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + token))
                .body(product)
                .post("/cart")
                .then().log().all()
                .statusCode(201);
    }

    @Test
    public void removeProductFromUserShoppingCart() {

        String token = authorize(USERNAME, PASSWORD);

        given().when().log().all()
                .contentType(ContentType.JSON)
                .header(new Header("Authorization", "Bearer " + token))
                .delete("/cart/1")
                .then().log().all()
                .statusCode(200);

    }

    private static void register(String login, String password){

        RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new User(login, password))
                .post("/register");
    }

    private String authorize(String login, String password){

        return RestAssured.given().log().all()
                .contentType(ContentType.JSON)
                .body(new User(login, password))
                .post("/login")
                .then().log().all()
                .extract().jsonPath().get("access_token");
    }
}
