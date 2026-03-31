import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';

@Injectable({
  providedIn: 'root'
})
export class MessageService {

  messages: string[] = [];

  constructor(private snackBar: MatSnackBar) {}

  add(message: string){
    this.messages.push(message);
  }

  notify(message: string, type: 'error' | 'success' | 'info' = 'info'){
    this.messages.push(message);
    this.snackBar.open(message, 'Dismiss', {
      duration: 4000,
      horizontalPosition: 'center',
      verticalPosition: 'top',
      panelClass: [`snack-${type}`]
    });
  }

  clear(){
    this.messages = [];
  }
}
