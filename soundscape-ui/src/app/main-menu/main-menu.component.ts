import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-main-menu',
  standalone: true,
  imports: [],
  templateUrl: './main-menu.component.html',
  styleUrl: './main-menu.component.css'
})
export class MainMenuComponent {
  constructor(private router: Router, private messageService: MessageService){}

  // Redirects user to the account details page
  account(){
    this.router.navigateByUrl('/account');
    this.messageService.clear();
  }

  searchProfiles(){
    this.router.navigateByUrl('/search-profiles');
    this.messageService.clear();
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
