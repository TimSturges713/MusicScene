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
        localStorage.setItem('other_username', u.username as string);
        localStorage.setItem('bio', u.bio as string);
        localStorage.setItem('city', u.city as string);
        localStorage.setItem('state', u.state);
        if(u.artistId){
          localStorage.setItem('artistId', u.artistId as string);
        }
        this.followingService.getFollowers(u.userId as number).subscribe((followers) =>{
          localStorage.setItem("followers_length", followers.length.toString());
          for(let i = 0; i < followers.length; i++){
            this.userService.getUserById(followers[i]).subscribe((follower) => {
              localStorage.setItem("follower " + i, follower.username as string);
            });
          }
        });
        
        this.router.navigateByUrl('/profile');
        return of(null);
      })
    ).subscribe();
  }

    
}
