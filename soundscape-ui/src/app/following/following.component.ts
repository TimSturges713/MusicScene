import { NgFor, NgIf } from '@angular/common';
import { Component } from '@angular/core';
import {Router} from '@angular/router';
import { UserService } from '../user.service';
import { FollowingService } from '../following.service';
import { MessageService } from '../message.service';
import { switchMap } from 'rxjs';
import { Location } from '@angular/common';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-following',
  standalone: true,
  imports: [NgFor, NgIf, MatButtonModule],
  templateUrl: './following.component.html',
  styleUrl: './following.component.css'
})
export class FollowingComponent {

  username = "";

  userId = 0;

  following: string[] = [];
  indicator = localStorage.getItem("indicator"); // indicates whether or not this page is for the logged in user or a viewed user


  constructor(private location: Location, private router: Router, private userService: UserService, private followingService: FollowingService, private messageService: MessageService){}

  ngOnInit() {
      if(this.indicator){ // if this is someone else's account, get their username, which has been stored already in LS by searchProfilesComp
        this.username = localStorage.getItem("other_username") as string;
      }
      else{ // this is your own account
        this.username = localStorage.getItem("username") as string;
      }
      this.userService.getUser(this.username).pipe( // get the user's following list
        switchMap(user => {
          this.userId = user.userId as number;
          return this.followingService.getFollowing(this.userId);
        })
      ).subscribe(followIds => {
        this.following = []; // reset array
        followIds.forEach(id => {
          this.userService.getUserById(id).subscribe(u => {
            this.following.push(u.username);
          });
        });
      });
    }
  
    unfollow(username: string){
      this.messageService.add("Unfollowed " + username);
      this.userService.getUser(this.username).subscribe(user => {
        this.userService.getUser(username).subscribe(u => {
          this.followingService.unfollowUser(user.userId as number, u.userId as number).subscribe();
          window.location.reload();
        })
      })
    }

  back(){
    this.location.back();
    localStorage.removeItem("indicator"); // reset the indicator for viewing another profile
    
    this.messageService.clear();
  }
}
