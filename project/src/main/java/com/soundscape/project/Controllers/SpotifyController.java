package com.soundscape.project.Controllers;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import io.github.cdimascio.dotenv.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.soundscape.project.Entities.User;
import com.soundscape.project.Repos.UserRepository;
import com.soundscape.project.Services.UserService;

import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;;

@RestController
@RequestMapping("/spotify")
@CrossOrigin(origins = "http://localhost:4200")
public class SpotifyController {
    private static final URI redirectURI = SpotifyHttpManager.makeUri("http://127.0.0.1:8080/spotify/user-code");

    private String code = "";

    @Autowired
    private UserRepository userRepository;

    @Autowired 
    private UserService userService;


    private static final Dotenv dotenv = Dotenv.load();

    private static final SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(dotenv.get("CLIENT_ID"))
            .setClientSecret(dotenv.get("CLIENT_SECRET"))
            .setRedirectUri(redirectURI)
            .build();

    @GetMapping("/user-code")
    public ResponseEntity<Map<String,String>> getUserCode(@RequestParam("code") String userCode,@RequestParam("state") String username, HttpServletResponse response){
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
            try{
                var r = spotifyApi.getCurrentUsersProfile().build().execute();
                User u = userRepository.findByUsername(username);
                try{
                    userService.updateSpotify(u, r.getId());
                }
                catch (DataIntegrityViolationException e) {
                    response.sendRedirect("http://localhost:4200/account?spotifyExist=true");
                    return new ResponseEntity<>(HttpStatus.OK);
                }              
            }
            catch(Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            response.sendRedirect("http://localhost:4200/account");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        
    }

    
    
    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("username") String username){
        
        AuthorizationCodeUriRequest authorizationCodeUriRequest = spotifyApi.authorizationCodeUri()
            .show_dialog(true)
            .state(username)
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
