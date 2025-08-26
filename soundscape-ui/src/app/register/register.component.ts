import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { MessageService } from '../message.service';
import { UserService } from '../user.service';
import { SpotifyService } from '../spotify.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent implements OnInit{
  constructor(private route: ActivatedRoute, private router: Router, private messageService: MessageService, private userService: UserService, private spotifyService: SpotifyService){}

  spotify = false;

  ngOnInit(){
  this.route.queryParams.subscribe(params => {
      if(params["spotifyExist"]){
        if(params["spotifyExist"] == "true"){
          this.messageService.add("NOTICE: Spotify account already exists with another user.")
        }
      }
      if(params["test"]){
        if(params["test"].equals("true")){
          this.spotify = true;
        }
      }
    });
  }

  back(){
    this.router.navigateByUrl('/login');
  }

  register(){
    this.messageService.add("Registering user");
    const username = (document.getElementById("username") as HTMLInputElement)?.value;
    const password = (document.getElementById("password") as HTMLInputElement)?.value;
    const email = (document.getElementById("email") as HTMLInputElement)?.value;
    const bio = (document.getElementById("bio") as HTMLInputElement)?.value;
    const city = (document.getElementById("city") as HTMLInputElement)?.value;
    const state = (document.getElementById("state") as HTMLInputElement)?.value;
  
    if(username == "" || password == "" || email == "" || city == "" || state == ""){
      this.messageService.add("Please fill out all fields");
      return;
    }
  
    
    this.userService.getUsers().subscribe(users => {
      const existingUser = users.find(user => user.username === username);
      if(existingUser){
        this.messageService.add("User already exists");
        return;
      }
      this.userService.addUser({username, bio, city, password, email, spotify: false, state: state, spotifyId: null, refreshToken: "", accessToken: "", artistId: null}).subscribe();
      localStorage.setItem("username", username);
      this.router.navigateByUrl('/spotify-register');
    })
  }

  
}
