package com.spotify.oauth2.api;

import io.restassured.response.Response;
import java.util.HashMap;
import static com.spotify.oauth2.api.Route.*;
import static com.spotify.oauth2.api.SpecBuilder.*;
import static io.restassured.RestAssured.given;

public class RestResource {
    public static Response post(String path, String token, Object requestPlaylist) {

        return
                given(getRequestSpecification())
                        .body(requestPlaylist)
                        .header("Authorization", "Bearer " + token)
                        .when().post(path)
                        .then().spec(getResponseSpecification())
                        .extract().response();
    }

    public static Response postAccount(HashMap<String, String> formParam) {
        return given(getAccountRequestSpecification())
                .formParams(formParam)
                .when().post(API + TOKEN)
                .then().spec(getResponseSpecification())
                .extract().response();
    }

    public static Response get(String path, String token) {
        return given(getRequestSpecification())
                .header("Authorization", "Bearer " + token)
                .when().get(path)
                .then().spec(getResponseSpecification())
                .extract()
                .response();

    }

    public static Response update(String path, String token, Object requestPlaylist) {
        return given(getRequestSpecification())
                .header("Authorization", "Bearer " + token)
                .body(requestPlaylist)
                .when().put(path)
                .then().spec(getResponseSpecification())
                .extract()
                .response();
    }
}
