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

  tracks: string[] = [];
  l: number = 0;

  constructor(private router: Router){}

  ngOnInit(){
    var length = localStorage.getItem("length");
    this.l = Number(length);
    for(var i = 0; i < this.l; i++){
      
      this.tracks.push(localStorage.getItem("track " + i) as string);
    }
  }

  back(){
    this.router.navigateByUrl("/songs");
    for(let i = 0; i < this.l; i++){
      localStorage.removeItem("track " + i);
    }
    localStorage.removeItem("length");
  }
}
