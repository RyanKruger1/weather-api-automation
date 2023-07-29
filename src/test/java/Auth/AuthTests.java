package Auth;

import jdk.jfr.Description;
import org.apache.http.HttpStatus;
import org.json.simple.JSONObject;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.weatherapp.core.BaseAPIClient;

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
    public void expiringTokenCreationTest() {

        authCommonMethods.
                createExpiringToken(validAuthObject).
                assertThat().
                statusCode(HttpStatus.SC_OK).
                spec(authCommonMethods.getExpiringTokenResponseModel());
    }

    @Description("As an API User, I attempt to create a non-expiring-token, find it and delete it.")
    public void nonExpiringTokenCreationTest() {

        authCommonMethods.
                createNonExpiringToken(validAuthObject).
                assertThat().
                statusCode(HttpStatus.SC_OK).
                spec(authCommonMethods.getNonExpiringTokenResponseModel());

        String tokenId = authCommonMethods.
                getAllNonExpiryTokens(validAuthObject).
                assertThat().
                statusCode(HttpStatus.SC_OK).
                extract().
                jsonPath().getString("tokens[0].key");

        authCommonMethods.
                deleteNonExpiringToken(validAuthObject, tokenId).
                assertThat().
                statusCode(HttpStatus.SC_NO_CONTENT);
    }
}