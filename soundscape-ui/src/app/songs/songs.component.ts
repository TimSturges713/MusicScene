import { Component, OnInit } from '@angular/core';
import { SpotifyService } from '../spotify.service';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';
import { Album, PagingObject, SimplifiedTrack } from '../album';
import { NgFor } from '@angular/common';
import { KeyValuePipe } from '@angular/common';

@Component({
  selector: 'app-songs',
  standalone: true,
  imports: [NgFor, KeyValuePipe],
  templateUrl: './songs.component.html',
  styleUrl: './songs.component.css'
})
export class SongsComponent implements OnInit{

  albumNames: string[] = [];
  originalOrder = () => 0;
  albumTracks: Map<string, string[]> = new Map<string, string[]>();

  constructor(private router: Router,private spotifyService: SpotifyService, private userService: UserService, private messageService: MessageService){};

  ngOnInit(): void {
    this.messageService.add("Loading Page...")
      this.getAlbums();
  }

  back(){
    this.router.navigateByUrl('/account')
    this.messageService.clear();
  }

  getTracks(tracks: string[]){
    localStorage.setItem("tracks", JSON.stringify(tracks));
    this.router.navigateByUrl("/album-tracks");
    this.messageService.clear();
  }

  getAlbums(){
    this.userService.getUser(localStorage.getItem("username") as string).subscribe((user) => {
      this.spotifyService.getAlbums(user).subscribe((albums) => {

        for(let i = 0; i < albums.length; i++){
          
          
          this.albumTracks.set(
            albums[i].name,
            albums[i].tracks.items.map(track => track.name) as string[]
          );
        }
      })
    })
  }
}
