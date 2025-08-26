import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from '../message.service';
import { UserService } from '../user.service';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [],
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  constructor(private router: Router, private messageService: MessageService, private userService: UserService){}

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
  
    if(username == null || password == null || email == null || city == null){
      this.messageService.add("Please fill out all fields");
      return;
    }
  
    this.userService.getUsers().subscribe(users => {
      const existingUser = users.find(user => user.username === username);
      if(existingUser){
        this.messageService.add("User already exists");
        return;
      }
      this.userService.addUser({username, bio, city, password, email, spotify: false, state: state, spotifyId: ""}).subscribe();
      this.messageService.add("User registered");
      localStorage.setItem("username", username);
      this.router.navigateByUrl('/main-menu');
    })
  }
}
