import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from '../message.service';
import { MatButtonModule } from '@angular/material/button';
import { GoogleMapsModule } from '@angular/google-maps';
import { CommunityService } from '../community.service';
import { Community } from '../Community';

@Component({
  selector: 'app-main-menu',
  standalone: true,
  imports: [MatButtonModule, GoogleMapsModule],
  templateUrl: './main-menu.component.html',
  styleUrl: './main-menu.component.css'
})
export class MainMenuComponent implements OnInit{
  constructor(private communityService: CommunityService, private router: Router, private messageService: MessageService){}

  center: google.maps.LatLngLiteral = {lat: 40.7128, lng: -74.0060};
  zoom = 12;
  
  circle_center: google.maps.LatLngLiteral = {lat: 0, lng: 0};
  circle_radius = 0;
  fillColor = "#f02e58";
  fillOpacity = 0.3;
  options:google.maps.CircleOptions = { 
    fillColor: this.fillColor,
    fillOpacity: this.fillOpacity
  };

  ngOnInit(){
    this.communityService.getCommunity("New York").subscribe((community) => {
      if(!community) return;
      this.circle_center = {lat: community.lat, lng: community.lng};
      this.circle_radius = community.radius;
    })
  }

  // Redirects user to the account details page
  account(){
    this.router.navigateByUrl('/account');
    this.messageService.clear();
  }

  searchProfiles(){
    this.router.navigateByUrl('/search-profiles');
    this.messageService.clear();
  }

  onCircleClick(event: google.maps.MapMouseEvent){
    let lat = event.latLng?.lat() as number
    let lng = event.latLng?.lng() as number

    
  }

  /**
   * Logs the user out 
   */
  logout(){
    localStorage.removeItem("username");
    this.router.navigateByUrl('/login');
    this.messageService.add("Logged out");
    let numofAlbums = Number(localStorage.getItem("aLength"));
    if(!numofAlbums){
      return;
    }
    for(let i = 0; i < numofAlbums; i++){
      let numofTracks = Number(localStorage.getItem("album " + i + " len"));
      for(let j = 0; j < numofTracks; j++){
        localStorage.removeItem("track " + i + " " + j)
      }
      localStorage.removeItem("album " + i);
      localStorage.removeItem("album " + i + "len");
    }
    localStorage.removeItem("aLength");
  }
}
