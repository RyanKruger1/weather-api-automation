package forecast;

import io.qameta.allure.Description;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;
import org.testng.annotations.Test;
import org.weatherapp.core.BaseAPIClient;

@Test
public class LocationTests extends BaseAPIClient {

    ForecastCommonMethods forecastCommonMethods = new ForecastCommonMethods();

    @Description("As an API user, I attempt to retrieve the location information for a commonly named city.")
    public void multipleCitiesTest() {
        forecastCommonMethods.
                getLocation(authHeader, forecastDetails.get("commonCityName").getAsString()).
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body("locations", Matchers.hasSize(Matchers.greaterThan(1)));
    }

    @Description("As an API user, I attempt to retrieve the location information for a uniquely named city.")
    public void singularCityTest() {
        forecastCommonMethods.
                getLocation(authHeader, forecastDetails.get("uniqueCityName").getAsString()).
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body("locations", Matchers.hasSize(Matchers.equalTo(1)));
    }

    @Description("As an API user, I attempt to retrieve the location information for a non-existent city.")
    public void nonExistentCityTest() {
        forecastCommonMethods.
                getLocation(authHeader, forecastDetails.get("nonExistentCityName").getAsString()).
                assertThat().
                statusCode(HttpStatus.SC_OK).
                body("locations", Matchers.hasSize(Matchers.equalTo(0)));
    }
}