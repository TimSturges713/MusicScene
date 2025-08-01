import { Component } from '@angular/core';
import { UserService } from '../user.service';
import { HttpClientModule } from '@angular/common/http';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [HttpClientModule],
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  constructor(private userService: UserService, private messageService: MessageService){}

  login(){
    const username = (document.getElementById("username") as HTMLInputElement)?.value;
    const password = (document.getElementById("password") as HTMLInputElement)?.value;
    this.messageService.add("Logging in user " + username);
    this.userService.login(username, password);
  }

}
