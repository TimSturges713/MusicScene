import { NgFor } from '@angular/common';
import { Component } from '@angular/core';
import {Router} from '@angular/router';
import { UserService } from '../user.service';
import { FollowingService } from '../following.service';
import { MessageService } from '../message.service';
import { switchMap } from 'rxjs';


@Component({
  selector: 'app-following',
  standalone: true,
  imports: [NgFor],
  templateUrl: './following.component.html',
  styleUrl: './following.component.css'
})
export class FollowingComponent {

  username = localStorage.getItem("username") as string;

  userId = 0;

  following: string[] = [];

  constructor(private router: Router, private userService: UserService, private followingService: FollowingService, private messageService: MessageService){}

  ngOnInit() {
      this.userService.getUser(this.username).pipe(
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
    this.router.navigateByUrl('/account');
    this.messageService.clear();
  }
}
