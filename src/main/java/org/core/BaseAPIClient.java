package org.core;

import com.google.gson.JsonObject;
import io.restassured.RestAssured;
import lombok.Getter;
import org.testng.annotations.BeforeMethod;

import java.io.IOException;

public class BaseAPIClient {

    protected static JsonReader jsonReader = new JsonReader();
    @Getter
    private static JsonObject testData;

    @BeforeMethod
    public static void setup() throws IOException {
        testData = jsonReader.readConfigData();
        JsonObject resourceDetails = testData.getAsJsonObject("resourceDetails");

        RestAssured.baseURI = resourceDetails.get("url").getAsString();
        RestAssured.basePath = resourceDetails.get("path").getAsString();
    }
}