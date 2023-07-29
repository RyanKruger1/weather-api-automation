package Auth;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class AuthCommonMethods {

    protected final String EXPIRING_TOKEN_PATH = "/token?expire_hours=2";
    protected final String NON_EXPIRING_TOKEN_PATH = "/token?expire_hours=-1";
    protected final String MANAGE_NON_EXPIRING_TOKENS_PATH = "/key/";

    public ValidatableResponse createExpiringToken(JSONObject authObject) {
        return given().
                body(authObject).
                when().
                log().all().
                post(EXPIRING_TOKEN_PATH).
                then().
                log().all();
    }

    public ValidatableResponse createNonExpiringToken(JSONObject authObject) {
        return given().
                body(authObject).
                when().
                log().all().
                post(NON_EXPIRING_TOKEN_PATH).
                then().
                log().all();
    }

    public ValidatableResponse getAllNonExpiryTokens(JSONObject object) {
        return given().
                body(object).
                when().
                log().all().
                post(MANAGE_NON_EXPIRING_TOKENS_PATH).
                then().
                log().all();
    }

    public ValidatableResponse deleteNonExpiringToken(JSONObject authObject, String id) {
        return given().
                body(authObject).
                when().
                log().all().
                delete(MANAGE_NON_EXPIRING_TOKENS_PATH + id).
                then().
                log().all();
    }

    public ResponseSpecification getNonExpiringTokenResponseModel() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectBody("access_token", notNullValue());
        responseSpecBuilder.expectBody("expires_in", nullValue());
        responseSpecBuilder.expectBody("token_type", is("bearer"));
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_OK);

        return responseSpecBuilder.build();
    }

    public ResponseSpecification getExpiringTokenResponseModel() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.expectBody("access_token", notNullValue());
        responseSpecBuilder.expectBody("expires_in", notNullValue());
        responseSpecBuilder.expectBody("token_type", is("bearer"));
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_OK);

        return responseSpecBuilder.build();
    }
}