package api.product;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class ProductTests {

    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268/products";
    }

    @Test
    public void getListProductsTest() {

        given().when().log().all()
                .contentType(ContentType.JSON)
                .get()
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void addNewProductTest() {

        createNewProduct()
                .then().log().all()
                .statusCode(201);
    }

    @Test
    public void getInformationAboutProduct() {
        given().when().log().all()
                .contentType(ContentType.JSON)
                .get("/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void updateInformationAboutProduct() {

        given().when().log().all()
                .contentType(ContentType.JSON)
                .body(randomProduct())
                .put("/1")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void deleteProduct() {

        given().when().log().all()
                .contentType(ContentType.JSON)
                .delete("/1")
                .then().log().all()
                .statusCode(200);
    }

    private String randomProduct() {
        String name = RandomStringUtils.randomAlphabetic(10);
        String category = RandomStringUtils.randomAlphanumeric(10);
        double price = 5.5;
        int discount = 1;

        return "{\n" +
                "  \"name\": \"" + name + ",\n" +
                "  \"category\": \"" + category + "\",\n" +
                "  \"price\": " + price + ",\n" +
                "  \"discount\": " + discount + "\n" +
                "}";
    }

    private Response createNewProduct() {
        return given().when().log().all()
                .contentType(ContentType.JSON)
                .body(randomProduct())
                .post();
    }
}
