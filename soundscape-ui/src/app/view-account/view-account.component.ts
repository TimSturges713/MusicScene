import { Component } from '@angular/core';
import { OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from '../message.service';
import { FollowingService } from '../following.service';
import { UserService } from '../user.service';
import { switchMap } from 'rxjs/operators';
import { of } from 'rxjs';
import { NgIf } from '@angular/common';

@Component({
  selector: 'app-view-account',
  standalone: true,
  imports: [NgIf],
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
    this.username = localStorage.getItem("other_username") as string;
    this.bio = localStorage.getItem("bio") as string;
    this.city = localStorage.getItem("city") as string;
    this.state = localStorage.getItem("state") as string;
    var test = localStorage.getItem("artist_id") as string;
    if(!test){
      this.checkArtistId = false;
    }
    else{
      this.checkArtistId = true;
      this.artistId = test;
    }
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

  unfollow(){
      const username = this.username;
      
      this.userService.getUser(localStorage.getItem("username") as string).subscribe(user => {
        this.userService.getUser(username).subscribe(u => {
          this.followingService.unfollowUser(user.userId as number, u.userId as number).subscribe();
          window.location.reload();
          this.messageService.add("Unfollowed " + username);
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
                window.location.reload();
                return this.followingService.followUser(me.userId as number, u.userId as number);

              } else {
                this.messageService.add("You are already following this user");
                return of(null);
              }
            })
          ))
        );
      })
    ).subscribe();

  }

  back(){
    localStorage.removeItem("other_username");
    localStorage.removeItem("bio");
    localStorage.removeItem("city");
    localStorage.removeItem("state");
    localStorage.removeItem("artist_id");
    var length = parseInt(localStorage.getItem("followers_length") as string);
    for(let i = 0; i < length; i++){
      localStorage.removeItem("follower" + i);
    }
    localStorage.removeItem("followers_length");
    this.router.navigateByUrl("/search-profiles");
    
  }

}
