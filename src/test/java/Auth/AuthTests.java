package Auth;


import jdk.jfr.Description;
import org.apache.http.HttpStatus;
import org.core.BaseAPIClient;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@Test
public class AuthTests extends BaseAPIClient {

    protected AuthCommonMethods authCommonMethods = new AuthCommonMethods();
    protected JSONObject validAuthObject;

    @BeforeMethod
    public void setupAuthEnvironment() {
        setAuthBasePath();
        validAuthObject = commonObject.getAuthRequestModel(username, password);
    }

    @Description("As an API User, I attempt to create a expiring-token.")
    public void expiringCreateTest() {

        authCommonMethods.
                createExpiringToken(validAuthObject).
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body("access_token", notNullValue()).
                body("expires_in", is(7200)).
                body("token_type", is("bearer"));
    }

    @Description("As an API User, I attempt to create a non-expiring-token, find it and delete it.")
    public void nonExpiringCreateTest() {

        authCommonMethods.
                createNonExpiringToken(validAuthObject).
                assertThat().
                statusCode(HttpStatus.SC_OK).
                spec(authCommonMethods.getExpiringTokenResponseModel());

        String tokenId = authCommonMethods.
                getAllNonExpiryTokens(validAuthObject).
                assertThat().statusCode(HttpStatus.SC_OK).
                extract().
                jsonPath().getString("tokens[0].key");

        authCommonMethods.
                deleteNonExpiringToken(validAuthObject, tokenId).
                assertThat().
                statusCode(HttpStatus.SC_NO_CONTENT);
    }
}