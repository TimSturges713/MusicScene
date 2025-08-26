import { Component } from '@angular/core';
import { SpotifyService } from '../spotify.service';
import { MessageService } from '../message.service';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from '../user.service';

@Component({
  selector: 'app-spotify-register',
  standalone: true,
  imports: [],
  templateUrl: './spotify-register.component.html',
  styleUrl: './spotify-register.component.css'
})
export class SpotifyRegisterComponent {

  constructor(private userService: UserService,private route: ActivatedRoute, private router: Router,private spotifyService: SpotifyService, private messageService: MessageService) {}

  ngOnInit(){
  this.route.queryParams.subscribe(params => {
      if(params["spotifyExist"]){
        if(params["spotifyExist"] == "true"){
          this.messageService.add("NOTICE: Spotify account already exists with another user.")
        }
      }
      
    });
  }

  spotifySetup(){
    this.spotifyService.requestAccountAccessRegister().subscribe((url) =>
    {
      window.location.href = url.url;
    }
    ,(error) => {
      this.messageService.add("Spotify account may be already in use by another user.")
    }
  );
  }

  cancel(){
    this.userService.getUser(localStorage.getItem("username") as string).subscribe((user)=> {
      this.userService.deleteUser(localStorage.getItem("username") as string).subscribe();
      localStorage.setItem("username", "");
      this.router.navigateByUrl('/login');
    })
    
  }
}
