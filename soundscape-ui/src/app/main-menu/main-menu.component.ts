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

  logout(){
    localStorage.removeItem("username");
    this.router.navigateByUrl('/login');
    this.messageService.add("Logged out");
  }
}
