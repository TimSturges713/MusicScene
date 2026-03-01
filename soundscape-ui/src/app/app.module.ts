import { NgModule }      from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { AppComponent }  from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { LoginComponent } from './login/login.component';
import { MessagesComponent } from './messages/messages.component';
import {MatInputModule} from '@angular/material/input';
import {MatCardModule} from '@angular/material/card';

@NgModule({
  imports:      [ BrowserModule ,
    AppRoutingModule, MatInputModule, MatCardModule
  ],
  declarations: [ AppComponent, LoginComponent, MessagesComponent],
  bootstrap:    [ AppComponent ]
})
export class AppModule { }
