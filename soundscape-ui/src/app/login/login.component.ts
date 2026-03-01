import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { HttpClientModule } from '@angular/common/http';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import {MatDividerModule} from '@angular/material/divider'

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [HttpClientModule, MatDividerModule, MatCardModule, MatInputModule, MatFormFieldModule],
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
