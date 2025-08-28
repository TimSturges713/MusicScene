import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { NgFor } from '@angular/common';

@Component({
  selector: 'app-album-tracks',
  standalone: true,
  imports: [NgFor],
  templateUrl: './album-tracks.component.html',
  styleUrl: './album-tracks.component.css'
})
export class AlbumTracksComponent implements OnInit{

  tracks: string = "";

  constructor(private router: Router){}

  ngOnInit(){
    this.tracks = localStorage.getItem("tracks") as string;
  }

  back(){
    this.router.navigateByUrl("/songs");
    localStorage.removeItem("tracks");
  }
}
