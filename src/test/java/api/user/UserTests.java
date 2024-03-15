package api.user;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;

public class UserTests {


    @BeforeAll
    public static void beforeAll() {
        RestAssured.baseURI = "http://9b142cdd34e.vps.myjino.ru:49268";
    }

    @Test
    public void registerNewUserTest() {

        String userData = randomUserData();

        createNewUser(userData)
                .then().log().all()
                .statusCode(201);
    }

    @Test
    public void loginWithUserNameAndPasswordTest() {

        String userData = randomUserData();

        createNewUser(userData)
                .then().log().all()
                .statusCode(201);

        given().when().log().all()
                .contentType(ContentType.JSON)
                .body(userData)
                .post("/login")
                .then().log().all()
                .statusCode(200);
    }

    private String randomUserData() {
        String username = RandomStringUtils.randomAlphabetic(10);
        String password = RandomStringUtils.randomAlphanumeric(10);

        return "{" +
                "\"username\": \"" + username + "\"," +
                "\"password\": \"" + password + "\"" +
                "}";
    }


    private Response createNewUser(String userData) {
        return given().when().log().all()
                .contentType(ContentType.JSON)
                .body(userData)
                .post("/register");
    }
}
