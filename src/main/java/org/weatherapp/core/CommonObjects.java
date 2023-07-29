package org.weatherapp.core;

import org.json.simple.JSONObject;

public class CommonObjects {
    public JSONObject getAuthRequestModel(String username, String password) {
        JSONObject authObject = new JSONObject();
        authObject.put("user", username);
        authObject.put("password", password);
        return authObject;
    }
}