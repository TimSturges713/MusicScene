package com.soundscape.project.Controllers;

import java.net.URI;

import org.springframework.web.bind.annotation.*;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;;

@RestController
@RequestMapping("/spotify")
@CrossOrigin(origins = "http://localhost:4200")
public class SpotifyController {
    private static final URI redirectURI = SpotifyHttpManager.makeUri("https://localhost:8080/spotify/user-code");


    SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId("YOUR_CLIENT_ID")
            .setClientSecret("YOUR_CLIENT_SECRET")
            .setRedirectUri(redirectURI)
            .build();

}
