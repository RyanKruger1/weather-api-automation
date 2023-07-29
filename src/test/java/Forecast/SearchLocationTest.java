package Forecast;

import jdk.jfr.Description;
import org.apache.http.HttpStatus;
import org.core.BaseAPIClient;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

@Test
public class SearchLocationTest extends BaseAPIClient {

    @Description("This is a test to test it all")
    public void validateLocationMunicipalitySearch() {
        given().
                header(getAuthHeader()).
                get("/location/search/Paarl").
                then().
                assertThat().
                statusCode(HttpStatus.SC_OK);
    }

    @AfterMethod
    public void burnDown() {
        System.out.println("This is an after test");
    }
}
