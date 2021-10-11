package com.spotify.oauth2.api;

import com.spotify.oauth2.utils.ConfigLoader;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static com.spotify.oauth2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.given;

public class TokenManager {
    private static String accessToken;
    private static Instant expiry_Time;

    public static String getToken() {
        try {
            System.out.println("Token is not healthy");
            System.out.println("Renewing token ...");
            if (accessToken == null || Instant.now().isAfter(expiry_Time)) {
                Response response = renewToken();
                accessToken = response.path("access_token");
                int expiryDurationInSeconds = response.path("expires_in");
                expiry_Time = Instant.now().plusSeconds(expiryDurationInSeconds - 300);
            } else {
                System.out.println("Token is healthy");
            }

        } catch (Exception e) {

        }
        return accessToken;
    }

    private static Response renewToken() {
        HashMap<String, String> formParam = new HashMap<>();
        formParam.put("client_id", ConfigLoader.getInstance().getClientId());
        formParam.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        formParam.put("grant_type", ConfigLoader.getInstance().getGrantType());
        formParam.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());

        Response response = RestResource.postAccount(formParam);
        if (response.statusCode() != 200) {
            throw new RuntimeException("Aborted!!! Renew token failed");
        }
        return response;
    }
}
