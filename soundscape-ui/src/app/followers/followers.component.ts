import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { FollowingService } from '../following.service';
import { switchMap } from 'rxjs';
import { NgFor } from '@angular/common';
import { of } from 'rxjs';
import { User } from '../user';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-followers',
  standalone: true,
  imports: [NgFor],
  templateUrl: './followers.component.html',
  styleUrl: './followers.component.css'
})
export class FollowersComponent implements OnInit{

  username = localStorage.getItem("username") as string;

  followers: string[] = [];

  userId = 0;

  constructor(private userService: UserService, private followingService: FollowingService, private messageService: MessageService, private router: Router) {}

  ngOnInit() {
    this.userService.getUser(this.username).pipe(
      switchMap(user => {
        this.userId = user.userId as number;
        return this.followingService.getFollowers(this.userId);
      })
    ).subscribe(followIds => {
      this.followers = []; // reset array
      followIds.forEach(id => {
        this.userService.getUserById(id).subscribe(u => {
          this.followers.push(u.username);
        });
      });
    });
  }

  unfollow(username: string){
    this.messageService.add("Unfollowed " + username);
    this.userService.getUser(this.username).subscribe(user => {
      this.userService.getUser(username).subscribe(u => {
        this.followingService.unfollowUser(user.userId as number, u.userId as number).subscribe();
      })
    })
  }

   /**
   * Redirects the user back to the main menu.
   */
  back(){
    this.router.navigateByUrl('/account');
  }
  
    follow() {
    const username = (document.getElementById("follow") as HTMLInputElement)?.value;
  
    this.userService.getUser(username).pipe(
      switchMap(u => {
        if (!u) {
          this.messageService.add("User not found");
          return of(null);
        }
        return this.userService.getUser(this.username).pipe(
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
    ).subscribe();
  }
}
