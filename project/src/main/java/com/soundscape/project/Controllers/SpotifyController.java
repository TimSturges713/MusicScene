package com.soundscape.project.Controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;;

@RestController
@RequestMapping("/spotify")
@CrossOrigin(origins = "http://localhost:4200")
public class SpotifyController {
    private static final URI redirectURI = SpotifyHttpManager.makeUri("http://127.0.0.1:8080/spotify/user-code");

    private String code = "";

    private static final Dotenv dotenv = Dotenv.load();

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(dotenv.get("CLIENT_ID"))
            .setClientSecret(dotenv.get("CLIENT_SECRET"))
            .setRedirectUri(redirectURI)
            .build();

    @GetMapping("/user-code")
    public ResponseEntity<Map<String,String>> getUserCode(@RequestParam("code") String userCode, HttpServletResponse response){
        code = userCode;
        
        AuthorizationCodeRequest authorizationCodeRequest = spotifyApi.authorizationCode(code)
            .build();
        
        try{
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
            spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            
            Map<String, String> map = new HashMap<>();
            map.put("AccessToken", spotifyApi.getAccessToken());
            map.put("ExpiresIn", authorizationCodeCredentials.getExpiresIn().toString());
            response.sendRedirect("http://localhost:4200/account");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        
    }

    
    
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
