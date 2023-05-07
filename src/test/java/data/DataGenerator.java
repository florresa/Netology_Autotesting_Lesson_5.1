package data;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;
import lombok.Value;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import com.github.javafaker.Faker;

import static io.restassured.RestAssured.given;

public class DataGenerator {

    private DataGenerator() {
    }

    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    private static final Faker faker = new Faker(new Locale("en"));

    @BeforeAll
    static void sendRequest(RegistrationDto registeredUser) {
        given()
                .spec(requestSpec)
                .body(registeredUser)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }


    public static String getRandomLogin() {
        String login = faker.name().username();
        return login;
    }

    public static String getRandomPassword() {
        String password = faker.internet().password();
        return password;
    }

    public static class Registration {
        private Registration() {
        }
    }

    public static RegistrationDto getUser(String status) {
        var user = new RegistrationDto(getRandomLogin(), getRandomPassword(), status);
        return user;
    }

    public static RegistrationDto getRegisteredUser(String status) {
        var registeredUser = getUser(status);
        sendRequest(registeredUser);
        return registeredUser;
    }

    @Value
    public static class RegistrationDto {
        String login;
        String password;
        String status;
    }
}

