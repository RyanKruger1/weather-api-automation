package org.weatherapp.core;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import io.restassured.http.Header;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;

import java.io.IOException;

import static io.restassured.RestAssured.given;

@Listeners(TestListener.class)
public class BaseAPIClient {

    protected static JsonReader jsonReader = new JsonReader();
    protected static JsonObject testData;
    protected static Header authHeader;
    protected static JsonObject resourceDetails;
    protected static JsonObject forecastDetails;
    protected static CommonObjects commonObject = new CommonObjects();
    protected static String username;
    protected static String password;


    @BeforeClass
    public static void setup() throws IOException {
        testData = jsonReader.readConfigData();

        resourceDetails = testData.getAsJsonObject("resourceDetails");
        forecastDetails = testData.getAsJsonObject("forecastData");

        username = resourceDetails.get("authUsername").getAsString();
        password = resourceDetails.get("authPassword").getAsString();

        RestAssured.baseURI = resourceDetails.get("url").getAsString();
        authHeader = getAuthHeader();
        setForecastBasePath();
    }

    protected static Header getAuthHeader() {
        setAuthBasePath();


        String accessToken = given().
                body(commonObject.getAuthRequestModel(username, password)).
                when().
                post("/token?expire_hours=0.5").
                then().
                extract().
                jsonPath().
                getString("access_token");

        return new Header("Authorization", "Bearer " + accessToken);
    }

    protected static void setAuthBasePath() {
        RestAssured.basePath = resourceDetails.get("authPath").getAsString();
    }

    protected static void setForecastBasePath() {
        RestAssured.basePath = resourceDetails.get("path").getAsString();
    }
}