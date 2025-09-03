import { Component } from '@angular/core';
import { MessageService } from '../message.service';
import { UserService } from '../user.service';
import { FollowingService } from '../following.service';
import { switchMap } from 'rxjs';
import { of } from 'rxjs';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-profiles',
  standalone: true,
  imports: [],
  templateUrl: './search-profiles.component.html',
  styleUrl: './search-profiles.component.css'
})
export class SearchProfilesComponent {

  username = localStorage.getItem("username") as string;

  constructor(private router: Router, private messageService: MessageService, private userService: UserService, private followingService: FollowingService){}

  back(){
    this.router.navigateByUrl('/main-menu');
  }
  
  search(){
    const username = (document.getElementById("search") as HTMLInputElement)?.value;
  
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
