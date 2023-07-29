package forecast;

import io.qameta.allure.Description;
import io.restassured.http.Header;
import org.apache.http.HttpStatus;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.weatherapp.core.BaseAPIClient;

@Test
public class ForecastNegativeTests extends BaseAPIClient {

    protected final String INVALID_ID = "0000000000";
    ForecastCommonMethods forecastCommonMethods = new ForecastCommonMethods();

    @Description("As an API user, I attempt to retrieve the daily forecast information using an invalid id.")
    public void retrieveTodayForecastInvalidIDTest() {
        String errorMessage =
                forecastCommonMethods.getDailyForecast(authHeader, INVALID_ID).
                        statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).
                        assertThat().
                        extract().body().asString();

        Assert.assertEquals(errorMessage, "\"Invalid id\"");
    }

    @Description("As an API user, I attempt to retrieve the daily forecast information using an invalid id.")
    public void retrieveHourlyForecastInvalidIDTest() {
        String errorMessage =
                forecastCommonMethods.getHourlyForecast(authHeader, INVALID_ID).
                        statusCode(HttpStatus.SC_UNPROCESSABLE_ENTITY).
                        assertThat().
                        extract().body().asString();

        Assert.assertEquals(errorMessage, "\"Invalid id\"");
    }

    @Description("As an API user, I attempt to retrieve the daily forecast information using an invalid auth header.")
    public void retrieveHourlyForecastInvalidAuthTest() {
        String errorMessage =
                forecastCommonMethods.getDailyForecast(
                                new Header("Authorization", "Bearer Invalid_Bearer"),
                                INVALID_ID).
                        assertThat().
                        statusCode(HttpStatus.SC_UNAUTHORIZED).
                        extract().
                        body().
                        asString();

        Assert.assertTrue(errorMessage.contains("erroneous token: Wrong number of segments"));
    }
}