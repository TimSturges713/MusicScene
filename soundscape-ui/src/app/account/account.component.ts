import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { NgFor, NgIf } from '@angular/common';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { error } from 'console';
import { FollowingService } from '../following.service';
import { User } from '../user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';
import { switchMap } from 'rxjs';
import { SpotifyService } from '../spotify.service';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [NgFor, NgIf],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent implements OnInit{

  // localstorage stores the username item already via login and we just retrieve it to display on account page
  username = localStorage.getItem("username") as string;

  connectedToSpotify: boolean = false;

  artistId: string|null = null;

  constructor(private route: ActivatedRoute, private router: Router, private messageService: MessageService, private userService: UserService, private followingService: FollowingService, 
    private http: HttpClient, private spotifyService: SpotifyService){}
  
  ngOnInit(){
    this.userService.getUser(this.username).subscribe((user) => {this.connectedToSpotify = user.spotify; 
      this.artistId = user.artistId;
    });
    this.route.queryParams.subscribe(params => {
      if(params["spotifyExist"]){
        if(params["spotifyExist"] == "true"){
          this.messageService.add("NOTICE: Spotify account already exists with another user.")
        }
      }
    });
  }

  addArtist(){
    const artist  = (document.getElementById("artistID") as HTMLInputElement)?.value;
    this.userService.getUser(this.username).subscribe((user) => {
      user.artistId = artist;
      this.userService.updateUser(user).subscribe();
    });
    
  }

  songs(){
    this.router.navigateByUrl("/songs");
  }

  disconnectSpotify(){
    this.userService.getUser(this.username).subscribe((user) => {
      user.artistId = null;
      this.userService.updateUser(user).subscribe();
    });
  }
  viewFollowing(){
    this.router.navigateByUrl("/following");
  }


  spotifySetup(){
    this.spotifyService.requestAccountAccessChange().subscribe((url) =>
    {
      window.location.href = url.url;
    }
    ,(error) => {
      this.messageService.add("Spotify account may be already in use by another user.")
    }
  );
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

viewFollowers(){
  this.router.navigateByUrl("/followers");
}


  /**
   * Redirects the user back to the main menu.
   */
  back(){
    this.router.navigateByUrl('/main-menu');
  }

  /**
   * Deletes a user's account from the database permanently and logs them out
   */
  deleteAccount(){
    this.userService.getUser(this.username as string).subscribe((user) => {
      this.followingService.deleteUserRecords(user);
    });
    this.userService.deleteUser(this.username).subscribe(
      (user) =>{
        this.messageService.add("Account Deleted");

        localStorage.removeItem("username");
        this.router.navigateByUrl('/login');
      },
      (error) => {
        this.messageService.add("Error deleting account");
        return;
      }
    );
    
  }
}
