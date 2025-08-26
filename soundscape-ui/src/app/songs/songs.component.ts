import { Component, OnInit } from '@angular/core';
import { SpotifyService } from '../spotify.service';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-songs',
  standalone: true,
  imports: [],
  templateUrl: './songs.component.html',
  styleUrl: './songs.component.css'
})
export class SongsComponent{
  
  constructor(private spotifyService: SpotifyService, private userService: UserService, private messageService: MessageService){};

  getAlbums(){
    this.userService.getUser(localStorage.getItem("username") as string).subscribe((user) => {
      this.spotifyService.getAlbums(user).subscribe((albums) => {
        this.messageService.add(albums.albums);
      })
    })
  }
}
