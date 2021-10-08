package com.spotify.oauth2.tests;

import com.spotify.oauth2.api.applicationApi.PlaylistApi;
import com.spotify.oauth2.pojo.ErrorPlaylist;
import com.spotify.oauth2.pojo.InnerError;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static com.spotify.oauth2.api.SpecBuilder.getRequestSpecification;
import static com.spotify.oauth2.api.SpecBuilder.getResponseSpecification;
import static io.restassured.RestAssured.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class PlaylistTests {

    @Test
    public void createAPlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("Pojo playlist3")
                .setDescription("Pojo playlist description3")
                .setPublic(false);
        Response response = PlaylistApi.post(requestPlaylist);
        assertThat(response.statusCode(), equalTo(201));
        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
    }

    @Test
    public void getPlaylist() {
        Playlist requestPlaylist = new Playlist()
                .setName("Updated playlist name");

        String playlistId = "68gvjryxxCUCQAU0kfIFCU";
        Response response = PlaylistApi.get(playlistId);
        assertThat(response.statusCode(), equalTo(201));
        Playlist responsePlaylist = response.as(Playlist.class);
        assertThat(responsePlaylist.getName(), equalTo(requestPlaylist.getName()));
    }

    @Test
    public void updateAPlaylist() {
        String payload = "{\n" +
                "  \"name\": \"New Playlist Moon 2 updated\",\n" +
                "  \"description\": \"New playlist description Moon\",\n" +
                "  \"public\": false\n" +
                "}";
        given(getRequestSpecification())
                .body(payload)
                .when().put("playlists/2H6tj7r7hiDofh9uojYpXV")
                .then().spec(getResponseSpecification())
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void notCreateAPlaylistWhenNameIsNull() {
        String payload = "{\n" +
                "  \"name\": \"\",\n" +
                "  \"description\": \"New playlist description Moon\",\n" +
                "  \"public\": false\n" +
                "}";
        ErrorPlaylist error = given(getRequestSpecification())
                .body(payload)
                .when().post("users/21u6hdaa3gxggmk2wkrec73ka/playlists")
                .then().spec(getResponseSpecification())
                .extract()
                .as(ErrorPlaylist.class);
        assertThat(error.getError().getStatus(), equalTo(401));
        assertThat(error.getError().getMessage(), equalTo("The access token expired"));
    }

    @Test
    public void notCreateAPlaylistWhenTokenIsExpired() {
        String token = "1234";
        Playlist requestPlaylist = new Playlist()
                .setName("Pojo playlist3")
                .setDescription("Pojo playlist description3")
                .setPublic(false);
        Response response = PlaylistApi.post(token, requestPlaylist);

        InnerError error = response.as(InnerError.class);
        assertThat(error.getStatus(), equalTo(401));
        assertThat(error.getMessage(), equalTo("The access token expired"));
    }
}
