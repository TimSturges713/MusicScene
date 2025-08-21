import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { HttpClientModule } from '@angular/common/http';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private userService: UserService, private messageService: MessageService, private router: Router){}


  /**
   * Logs a user in using UserServices depending on whether the username and password are valid.
   * Takes parameters via HTML Input Elements in the textboxes on the login page.
   */
  login(){
    const username = (document.getElementById("username") as HTMLInputElement)?.value;
    const password = (document.getElementById("password") as HTMLInputElement)?.value;
    if(!username || !password){
      this.messageService.add("Please enter a username and password");
      return;
    }
    this.messageService.add("Logging in user " + username);
    this.userService.login(username, password);
  }

  /**
   * Redirects user to register user page.
   */
  register(){
    this.router.navigateByUrl('/register');
  }
}
