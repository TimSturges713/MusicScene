import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { UserService } from '../user.service';
import { MessageService } from '../message.service';
import { error } from 'console';
import { FollowingService } from '../following.service';
import { User } from '../user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable, of } from 'rxjs';

@Component({
  selector: 'app-account',
  standalone: true,
  imports: [],
  templateUrl: './account.component.html',
  styleUrl: './account.component.css'
})
export class AccountComponent {

  // localstorage stores the username item already via login and we just retrieve it to display on account page
  username = localStorage.getItem("username") as String;

  private followUrl = "http://localhost:8080/users/following";



  constructor(private router: Router, private messageService: MessageService, private userService: UserService, private followingService: FollowingService, private http: HttpClient){}
  
  follow(){
    const username = (document.getElementById("follow") as HTMLInputElement)?.value;
    
    this.userService.getUser(username).subscribe((u) =>{
      if(u == null){
        this.messageService.add("User not found");
        return;
      }
      var user = u.userId as number;
      this.userService.getUser(this.username as string).subscribe((u) =>{
      var me = u.userId as number;
      this.followingService.followUser(me as number, user as number).subscribe();
    }
    );
    });
    
    
    
    
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
