import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from '../message.service';
import { FollowingService } from '../following.service';
import { UserService } from '../user.service';
import { switchMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { NgIf } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';
import { MatDividerModule } from '@angular/material/divider';

@Component({
  selector: 'app-view-account',
  standalone: true,
  imports: [NgIf, MatButtonModule, MatDividerModule],
  templateUrl: './view-account.component.html',
  styleUrl: './view-account.component.css'
})
export class ViewAccountComponent implements OnInit{

  constructor(private router:Router, private userService:UserService, private messageService:MessageService, private followingService:FollowingService){}


  username:string = "";
  bio:string = "";
  city = "";
  state = "";
  artistId = "";
  checkArtistId = false;
  following = false;

  ngOnInit(){
    var tmp = localStorage.getItem("tmp");
    if(tmp){
      localStorage.setItem("username", tmp);
      localStorage.removeItem("tmp");
    }
    this.username = localStorage.getItem("other_username") as string;
    this.bio = localStorage.getItem("bio") as string;
    this.city = localStorage.getItem("city") as string;
    this.state = localStorage.getItem("state") as string;
    this.artistId = localStorage.getItem("artistId") as string;
    
    this.userService.getUser(this.username).subscribe(u => {
      this.userService.getUser(localStorage.getItem("username") as string).subscribe(me => {
        this.followingService.getSpecifiedFollowing(me.userId as number, u.userId as number).subscribe(obj => {
          if(obj){
            this.following = true;
          } else {
            this.following = false;
          }
        });
      });
    });
    
  }

  getMusic(){
    localStorage.setItem("otherUsersAlbum", "true"); // lets the album page know this is another users albums, not their own, true is true, null is false
    this.router.navigateByUrl("/songs"); // navigate to the album page
  }

  navi_following(){
    localStorage.setItem("indicator", "true"); // indicate to the following page that this is a different user than the one signed in
    
    this.router.navigateByUrl("/following");
  }

  followers(){
    localStorage.setItem("indicator", "true"); // indicate to the followers page that this is a different user than the one signed in
  
    this.router.navigateByUrl("/followers");
  }

  unfollow(){
      const username = this.username;

      this.userService.getUser(localStorage.getItem("username") as string).subscribe(user => {
        this.userService.getUser(username).subscribe(u => {
          this.followingService.unfollowUser(user.userId as number, u.userId as number).subscribe(() => {
            this.following = false;
            this.messageService.add("Unfollowed " + username);
          });
        })
      })
    }

  follow() {
    const username = this.username;
    this.userService.getUser(username).pipe(
      switchMap(u => {
        if (!u) {
          this.messageService.add("User not found");
          return of(null);
        }
        return this.userService.getUser(localStorage.getItem("username") as string).pipe(
          switchMap(me => this.followingService.getSpecifiedFollowing(me.userId as number, u.userId as number).pipe(
            switchMap(obj => {
              if (!obj) {
                return this.followingService.followUser(me.userId as number, u.userId as number);
              } else {
                this.messageService.add("You are already following this user");
                return of(null);
              }
            })
          ))
        );
      })
    ).subscribe(result => {
      if (result !== null) {
        this.following = true;
      }
    });

  }

  back(){
    localStorage.removeItem("other_username");
    localStorage.removeItem("bio");
    localStorage.removeItem("city");
    localStorage.removeItem("state");
    localStorage.removeItem("artistId");

    if(localStorage.getItem("viewedByAnother") == "true"){ // if you viewed this persons music, clear the cache
      let numofAlbums = Number(localStorage.getItem("aLength"));
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
    
    this.router.navigateByUrl("/search-profiles");
    
  }

}
