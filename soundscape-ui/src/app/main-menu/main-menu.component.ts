import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { MessageService } from '../message.service';

@Component({
  selector: 'app-main-menu',
  standalone: true,
  imports: [],
  templateUrl: './main-menu.component.html',
  styleUrl: './main-menu.component.css'
})
export class MainMenuComponent {
  constructor(private router: Router, private messageService: MessageService){}

  // Redirects user to the account details page
  account(){
    this.router.navigateByUrl('/account');
    this.messageService.clear();
  }

  /**
   * Logs the user out 
   */
  logout(){
    localStorage.removeItem("username");
    this.router.navigateByUrl('/login');
    this.messageService.add("Logged out");
  }
}
