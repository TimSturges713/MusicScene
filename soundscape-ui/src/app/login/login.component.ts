import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { HttpClientModule } from '@angular/common/http';
import { MessageService } from '../message.service';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatCardModule } from '@angular/material/card';
import { MatDividerModule } from '@angular/material/divider';
import { MatButtonModule } from '@angular/material/button';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [HttpClientModule, FormsModule, MatDividerModule, MatCardModule, MatInputModule, MatFormFieldModule, MatButtonModule],
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
      this.messageService.notify("Please enter a username and password", 'error');
      return;
    } 
    this.userService.login(username, password);
  }

  /**
   * Redirects user to register user page.
   */
  register(){
    this.router.navigateByUrl('/register');
  }
}
