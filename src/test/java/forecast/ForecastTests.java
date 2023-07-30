package forecast;

import io.qameta.allure.Description;
import org.apache.http.HttpStatus;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.weatherapp.core.BaseAPIClient;

@Test
public class ForecastTests extends BaseAPIClient {

    protected static String location;
    ForecastCommonMethods forecastCommonMethods = new ForecastCommonMethods();

    @BeforeMethod
    public void setLocation() {
        location =
                forecastCommonMethods.
                        getLocation(authHeader, forecastDetails.get("uniqueCityName").getAsString()).
                        extract().
                        jsonPath().
                        getString("locations[0].id");
    }

    @Description("As an API user, I attempt to retrieve the daily forecast information for a uniquely named city.")
    public void retrieveTodayForecastInformationTest() {
        forecastCommonMethods.getDailyForecast(authHeader, location).
                statusCode(HttpStatus.SC_OK).
                assertThat().
                spec(forecastCommonMethods.getDailyForecastResponseModel());
    }

    @Description("As an API user, I attempt to retrieve the hourly forecast information for a uniquely named city.")
    public void retrieveHourlyForecastInformationTest() {
        forecastCommonMethods.getHourlyForecast(authHeader, location).
                statusCode(HttpStatus.SC_OK).
                assertThat().
                spec(forecastCommonMethods.getHourlyForecastResponseModel());
    }
}