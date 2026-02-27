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
  numOfTracks: number = 0;
  

  constructor(private router: Router){}

  ngOnInit(){
    
    var length = localStorage.getItem("length");  // retrieve numOfTracks from album page
    this.numOfTracks = Number(length);  
    for(var i = 0; i < this.numOfTracks; i++){
      this.tracks.push(localStorage.getItem("track " + i) as string); // add track names to global list so HTML can display them
    }
  }

  back(){
    for(let i = 0; i < this.numOfTracks; i++){  // iterate through tracks in cache to reset it for another album
      localStorage.removeItem("track " + i);
    }
    localStorage.removeItem("length");  // reset length in cache for the next album to be selected
    this.router.navigateByUrl("/songs");
    
  }
}
