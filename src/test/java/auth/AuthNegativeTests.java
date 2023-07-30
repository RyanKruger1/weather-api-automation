package auth;

import io.qameta.allure.Description;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.weatherapp.core.BaseAPIClient;

@Test
public class AuthNegativeTests extends BaseAPIClient {

    public final String INVALID_USERNAME = "invalid_username";
    public final String INVALID_PASSWORD = "invalid_password";
    protected AuthCommonMethods authCommonMethods = new AuthCommonMethods();

    @BeforeMethod
    public void setupAuthEnvironment() {
        setAuthBasePath();
    }

    @Description("As an API User, I attempt to retrieve a expiring-token using invalid credentials.")
    public void expiringTokenInvalidCredentialsTest() {
        authCommonMethods.
                createExpiringToken(commonObject.
                        getAuthRequestModel(
                                INVALID_USERNAME,
                                INVALID_PASSWORD)).
                assertThat().
                statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Description("As an API User, I attempt to retrieve a expiring-token using an invalid password.")
    public void expiringTokenInvalidPasswordTest() {
        authCommonMethods.createExpiringToken(
                        commonObject.
                                getAuthRequestModel(
                                        username,
                                        INVALID_PASSWORD)).
                assertThat().
                statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Description("As an API User, I attempt to create a non-expiring-token using invalid credentials.")
    public void nonExpiringTokenInvalidCredentialsTest() {
        authCommonMethods.
                createNonExpiringToken(
                        commonObject.
                                getAuthRequestModel(
                                        INVALID_USERNAME,
                                        INVALID_PASSWORD)).
                assertThat().
                statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Description("As an API User, I attempt to create a non-expiring-token using an invalid password.")
    public void nonExpiringTokenInvalidPasswordTest() {
        authCommonMethods.
                createNonExpiringToken(
                        commonObject.
                                getAuthRequestModel(
                                        username,
                                        INVALID_PASSWORD)).
                assertThat().
                statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Description("As an API User, I attempt to retrieve all non-expiring-token's using an invalid bearer token.")
    public void nonExpiringTokenInvalidRetrievalTest() {
        authCommonMethods.
                getAllNonExpiryTokens(
                        commonObject.getAuthRequestModel(INVALID_USERNAME, INVALID_PASSWORD)).
                assertThat().
                statusCode(HttpStatus.SC_UNAUTHORIZED);
    }

    @Description("As an API User, I attempt to delete a non-expiring-token using an invalid token-id.")
    public void nonExpiringInvalidTokenDeletionTest() {
        authCommonMethods.
                deleteNonExpiringToken(
                        commonObject.
                                getAuthRequestModel(
                                        username,
                                        password),
                        "12345").
                assertThat().
                statusCode(HttpStatus.SC_NOT_ACCEPTABLE);
    }
}