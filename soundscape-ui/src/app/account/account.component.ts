import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { error } from 'console';


@Component({
  selector: 'app-account',
  standalone: true,
  imports: [],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent {

  username = localStorage.getItem("username") as String;

  constructor(private router: Router, private messageService: MessageService, private userService: UserService){}
  
  

  back(){
    this.router.navigateByUrl('/main-menu');
  }

  deleteAccount(){
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
