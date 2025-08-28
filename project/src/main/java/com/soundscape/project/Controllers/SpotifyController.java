package com.soundscape.project.Controllers;

import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.github.cdimascio.dotenv.*;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import com.soundscape.project.Entities.User;
import com.soundscape.project.Repos.UserRepository;
import com.soundscape.project.Services.UserService;

import se.michaelthelin.spotify.model_objects.specification.Album;
import se.michaelthelin.spotify.model_objects.specification.AlbumSimplified;
import se.michaelthelin.spotify.model_objects.specification.Paging;
import se.michaelthelin.spotify.SpotifyApi;
import se.michaelthelin.spotify.SpotifyHttpManager;
import se.michaelthelin.spotify.enums.AlbumType;
import se.michaelthelin.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;
import se.michaelthelin.spotify.requests.authorization.authorization_code.AuthorizationCodeUriRequest;
import se.michaelthelin.spotify.requests.data.artists.GetArtistsAlbumsRequest;
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

    

    @GetMapping("/user-code")
    public ResponseEntity<Map<String,String>> getUserCode(@RequestParam("code") String userCode,@RequestParam("state") String state, HttpServletResponse response){
        code = userCode;

        SpotifyApi startSpotifyApi = new SpotifyApi.Builder()
            .setClientId(dotenv.get("CLIENT_ID"))
            .setClientSecret(dotenv.get("CLIENT_SECRET"))
            .setRedirectUri(redirectURI)
            .build();
        AuthorizationCodeRequest authorizationCodeRequest = startSpotifyApi.authorizationCode(code)
            .build();
        
        try{
            final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeRequest.execute();
            startSpotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
            startSpotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
            
            Map<String, String> map = new HashMap<>();
            String decodedState = URLDecoder.decode(state, StandardCharsets.UTF_8);
            String[] parts = decodedState.split(" ");
            String username = parts[0];
            String registerFlag = parts[1];
            map.put("AccessToken", startSpotifyApi.getAccessToken());
            map.put("ExpiresIn", authorizationCodeCredentials.getExpiresIn().toString());
            try{
                var r = startSpotifyApi.getCurrentUsersProfile().build().execute();
                User u = userRepository.findByUsername(username);
                try{
                    userService.updateSpotify(u, r.getId(), authorizationCodeCredentials.getAccessToken(), authorizationCodeCredentials.getRefreshToken());
                    try {
                        startSpotifyApi.getCurrentUsersProfile().build().execute();
                        System.out.println("Token is valid!");
                    } catch (Exception e) {
                        return new ResponseEntity<>(HttpStatus.ALREADY_REPORTED);
                    }
                }
                catch (DataIntegrityViolationException e) {
                    
                    
                    if(registerFlag.equals("false")){
                      response.sendRedirect("http://localhost:4200/account?spotifyExist=true");
                      return new ResponseEntity<>(HttpStatus.OK);
                    }
                    
                    response.sendRedirect("http://localhost:4200/spotify-register?spotifyExist=true");
                    return new ResponseEntity<>(HttpStatus.OK);
                }              
            }
            catch(Exception e){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            if(registerFlag.equals("false")){
                response.sendRedirect("http://localhost:4200/account");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            response.sendRedirect("http://localhost:4200/main-menu");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        
    }

    @EventListener(ApplicationReadyEvent.class)
    public void refreshTokensOnStartup() {
        refreshAllTokens();
    }


    @Scheduled(fixedRate = 60 * 30 * 1000) // every 30 mins
    public void refreshAllTokens() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            if (user.getRefreshToken() != null) {
                try {
                    SpotifyApi api = new SpotifyApi.Builder()
                        .setClientId(dotenv.get("CLIENT_ID"))
                        .setClientSecret(dotenv.get("CLIENT_SECRET"))
                        .setRefreshToken(user.getRefreshToken())
                        .build();

                    AuthorizationCodeCredentials creds = api.authorizationCodeRefresh().build().execute();
                    user.setAccessToken(creds.getAccessToken());
                    if (creds.getRefreshToken() != null) {
                        user.setRefreshToken(creds.getRefreshToken());
                    }
                    userRepository.save(user);
                    
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
    }   
}

    
    @GetMapping("/login")
    public ResponseEntity<Map<String, String>> login(@RequestParam("username") String username, @RequestParam("register") String register){
        SpotifyApi startSpotifyApi = new SpotifyApi.Builder()
            .setClientId(dotenv.get("CLIENT_ID"))
            .setClientSecret(dotenv.get("CLIENT_SECRET"))
            .setRedirectUri(redirectURI)
            .build();
        AuthorizationCodeUriRequest authorizationCodeUriRequest = startSpotifyApi.authorizationCodeUri()
            .show_dialog(true)
            .scope("user-read-email user-library-read user-follow-read")
            .state(URLEncoder.encode(username + " " + register, StandardCharsets.UTF_8))
            .build();
        URI uri = authorizationCodeUriRequest.execute();
        if(uri.toString() == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        

        Map<String, String> map = new HashMap<>();
        map.put("url", uri.toString());

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @GetMapping("/artist-albums")
    public ResponseEntity<Album[]> getArtistAlbums(@RequestParam("username") String username){
         SpotifyApi spotifyApi = new SpotifyApi.Builder()
            .setClientId(dotenv.get("CLIENT_ID"))
            .setClientSecret(dotenv.get("CLIENT_SECRET"))
            .setRedirectUri(redirectURI)
            .build();
        spotifyApi.setAccessToken(userRepository.findByUsername(username).getAccessToken());
        spotifyApi.setRefreshToken(userRepository.findByUsername(username).getRefreshToken());
         GetArtistsAlbumsRequest getArtistsAlbumsRequest = spotifyApi.getArtistsAlbums(userRepository.findByUsername(username).getArtistId())
         .limit(20).build();
         
         try{
            Paging<AlbumSimplified> a = getArtistsAlbumsRequest.execute();
            
            AlbumSimplified[] albums= a.getItems();
            String[] albumIds = new String[albums.length];
            for(int i = 0; i < albums.length; i++){
                albumIds[i] = albums[i].getId();
            }
            
            Album[] fullAlbum = spotifyApi.getSeveralAlbums(albumIds).build().execute();
            List<Album> albumList = new ArrayList<>();
            for(int i = 0; i < fullAlbum.length; i++){
                if(fullAlbum[i].getAlbumType() == AlbumType.ALBUM){
                    albumList.add(fullAlbum[i]);
                }
            }
            Album[] f = new Album[albumList.size()];
            for(int i = 0; i < albumList.size(); i++){
                f[i] = albumList.get(i);
            }
            
            return new ResponseEntity<>(f, HttpStatus.OK);
         }catch(Exception e){
            refreshAllTokens();
            try{
                Paging<AlbumSimplified> a = getArtistsAlbumsRequest.execute();
            
            AlbumSimplified[] albums= a.getItems();
            String[] albumIds = new String[albums.length];
            for(int i = 0; i < albums.length; i++){
                albumIds[i] = albums[i].getId();
            }
            
            Album[] fullAlbum = spotifyApi.getSeveralAlbums(albumIds).build().execute();
            List<Album> albumList = new ArrayList<>();
            for(int i = 0; i < fullAlbum.length; i++){
                if(fullAlbum[i].getAlbumType() == AlbumType.ALBUM){
                    albumList.add(fullAlbum[i]);
                }
            }
            Album[] f = new Album[albumList.size()];
            for(int i = 0; i < albumList.size(); i++){
                f[i] = albumList.get(i);
            }
            
            return new ResponseEntity<>(f, HttpStatus.OK);
            }
            catch(Exception f){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        
    }
}
