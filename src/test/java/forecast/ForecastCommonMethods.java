package forecast;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.Header;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;
import org.hamcrest.Matchers;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.notNullValue;

public class ForecastCommonMethods {

    static String currentHourDateTime;
    static String currentDayDateTime;
    protected final String LOCATION_PATH = "/location/search/";
    protected final String FORECAST_DAILY_PATH = "/forecast/daily/";
    protected final String FORECAST_HOURLY_PATH = "/forecast/hourly/";

    public ForecastCommonMethods() {
        currentHourDateTime = OffsetDateTime.
                now().
                atZoneSameInstant(ZoneOffset.ofHours(2)).
                format(DateTimeFormatter.
                        ofPattern("yyyy-MM-dd'T'HH:00XXX"));

        currentDayDateTime = LocalDate.
                now().
                format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

    public ValidatableResponse getLocation(Header auth, String city) {
        return given().
                header(auth).
                when().
                log().all().
                post(LOCATION_PATH + city).
                then().
                log().all();
    }

    public ValidatableResponse getDailyForecast(Header auth, String cityId) {
        return given().
                header(auth).
                when().
                log().all().
                post(FORECAST_DAILY_PATH + cityId).
                then().
                log().all();
    }

    public ValidatableResponse getHourlyForecast(Header auth, String cityId) {
        return given().
                header(auth).
                when().
                log().all().
                post(FORECAST_HOURLY_PATH + cityId).
                then().
                log().all();
    }

    public ResponseSpecification getDailyForecastResponseModel() {
        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.rootPath("forecast[0]");
        responseSpecBuilder.expectBody("date", hasToString(currentDayDateTime));
        responseSpecBuilder.expectBody("maxTemp", notNullValue());
        responseSpecBuilder.expectBody("minTemp", notNullValue());
        responseSpecBuilder.expectBody("windDir", notNullValue());
        responseSpecBuilder.expectBody("maxWindSpeed", notNullValue());
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_OK);

        return responseSpecBuilder.build();
    }

    public ResponseSpecification getHourlyForecastResponseModel() {

        ResponseSpecBuilder responseSpecBuilder = new ResponseSpecBuilder();
        responseSpecBuilder.rootPath("forecast[0]");
        responseSpecBuilder.expectBody("time", Matchers.hasToString(currentHourDateTime));
        responseSpecBuilder.expectBody("temperature", notNullValue());
        responseSpecBuilder.expectBody("feelsLikeTemp", notNullValue());
        responseSpecBuilder.expectBody("windSpeed", notNullValue());
        responseSpecBuilder.expectBody("windGust", notNullValue());
        responseSpecBuilder.expectBody("windDirString", notNullValue());
        responseSpecBuilder.expectStatusCode(HttpStatus.SC_OK);

        return responseSpecBuilder.build();
    }
}