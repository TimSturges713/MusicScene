package com.soundscape.project.Controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;;

@RestController
@RequestMapping("/spotify")
@CrossOrigin(origins = "http://localhost:4200")
public class SpotifyController {
    private static final URI redirectURI = SpotifyHttpManager.makeUri("http://127.0.0.1:8080/spotify/user-code");

    private static final Dotenv dotenv = Dotenv.load();

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(dotenv.get("CLIENT_ID"))
            .setClientSecret(dotenv.get("CLIENT_SECRET"))
            .setRedirectUri(redirectURI)
            .build();

    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login(){
        
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .show_dialog(true)
            .build();
        URI uri = authorizationCodeUriRequest.execute();
        if(uri.toString() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        

        Map<String, String> map = new HashMap<>();
        map.put("url", uri.toString());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
}
