import { Component, OnInit } from '@angular/core';
import { SpotifyService } from '../spotify.service';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';
import { Album } from '../album';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-songs',
  standalone: true,
  imports: [NgFor],
  templateUrl: './songs.component.html',
  styleUrl: './songs.component.css'
})
export class SongsComponent implements OnInit{

  albumNames: string[] = [];

  constructor(private router: Router,private spotifyService: SpotifyService, private userService: UserService, private messageService: MessageService){};

  ngOnInit(): void {
    this.messageService.add("Loading Page...")
      this.getAlbums();
  }

  back(){
    this.router.navigateByUrl('/account');
  }

  getAlbums(){
    this.userService.getUser(localStorage.getItem("username") as string).subscribe((user) => {
      this.spotifyService.getAlbums(user).subscribe((albums) => {

        for(let i = 0; i < albums.length; i++){
          
          this.albumNames.push(albums[i].name);
        }
      })
    })
  }
}
