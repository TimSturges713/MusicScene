import { Component, OnInit } from '@angular/core';
import { SpotifyService } from '../spotify.service';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';
import { Album, PagingObject, SimplifiedTrack } from '../album';
import { NgFor } from '@angular/common';
import { KeyValuePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-songs',
  standalone: true,
  imports: [NgFor, KeyValuePipe],
  templateUrl: './songs.component.html',
  styleUrl: './songs.component.css'
})
export class SongsComponent implements OnInit{
  otherUsersAlbum = "";
  cacheIndicator: boolean = false;
  albumNames: string[] = [];
  originalOrder = () => 0;
  albumTracks: Map<string, string[]> = new Map<string, string[]>();

  constructor(private route: ActivatedRoute, private router: Router,private spotifyService: SpotifyService, private userService: UserService, private messageService: MessageService){};

  ngOnInit(): void {
    
    this.otherUsersAlbum = localStorage.getItem("otherUsersAlbum") as string;
    this.getAlbums(); // call getAlbums
    this.messageService.add("Loading Page...");
    
    
  }

  back(){
    localStorage.removeItem("otherUsersAlbum");
    if(this.otherUsersAlbum == "true"){
      localStorage.setItem("viewedByAnother", "true"); // This is to clear cache if this is another person's profile, when they exit that profile album cache is cleared
      this.router.navigateByUrl("/profile");
    }
    else{
    this.router.navigateByUrl('/account');
    }
    this.messageService.clear();
  }

  getTracks(tracks: string[]){
    for(let i = 0; i < tracks.length; i++){ //iterate through the tracks
      localStorage.setItem("track " + i, tracks[i]);  // copy track name to localStorage for track page to carry over, saved as track 'trackNum' = trackName
    }
    
    localStorage.setItem("length", tracks.length.toString()); // copy numOfTracks to localStorage to carry over, length = numOfTracks
    this.router.navigateByUrl("/album-tracks"); // redirect
    this.messageService.clear();
  }

  getAlbums(){
    if(localStorage.getItem("album 1") != null){  // if there is a cached album 1, load it back
      let length = Number(localStorage.getItem("aLength")); // Grab aLength from the cache (the amount of albums the artist has)
      for(let i = 0; i < length; i++){  // iterate from album 0 to length-1
        let albumLen = Number(localStorage.getItem("album " + i + " len")); // get numOfTracks from cache
        let tracks = [];
        for(let j = 0; j < albumLen; j++){  // iterate through the albums tracks, the jth track
          tracks[j] = localStorage.getItem("track " + i + " " + j) as string; // get j-th trackName and set to cached track j
        }
        this.albumTracks.set( // set the global albums and tracks so the HTML can see it
          localStorage.getItem("album " + i) as string, // get album name
          tracks  // the tracks of the album in string[]
        );
      }
    }
    else{   // initial load, not cached yet
      let profile = "";
      if(this.otherUsersAlbum == "true"){
        profile = localStorage.getItem("other_username") as string;
      }
      else{
        profile = localStorage.getItem("username") as string;
      }
      
      this.userService.getUser(profile).subscribe((user) => {  //get current user
      this.spotifyService.getAlbums(user, this.otherUsersAlbum).subscribe((albums) => { // gets the albums from Spotify API
        localStorage.setItem("aLength", String(albums.length)); // gets the length of the list of albums and puts it in the cache
        for(let i = 0; i < albums.length; i++){   // iterate through the albums
          
          localStorage.setItem("album " + i, albums[i].name); // album num 0 - len-1, set these names of them to the cache

          let numOfTracks = albums[i].tracks.items.length; // get the total num of tracks in this album
          localStorage.setItem("album " + i + " len", String(numOfTracks)); // adds| album 'album num' len = 'numOfTracks' |to cache to store len
          
          for(let j = 0; j < numOfTracks; j++){    // iterate through the tracks and add them to cache
            let trackName = albums[i].tracks.items[j].name; // get the name of the track
            localStorage.setItem("track " + i + " " + j,  trackName as string); // add the track's name to cache: track 'alb num' 'track num' = 'trackName'
          }

          this.albumNames.push(albums[i].name); // push to albumNames list the name of this album in the iteration
          this.albumTracks.set( // in albumTracks, the global list of this component, set name and tracks for the HTML view
            albums[i].name,
            albums[i].tracks.items.map(track => track.name) as string[]
          );
        }
      })
    })
  }
  }
}
