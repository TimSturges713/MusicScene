import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from 'express';
import { Community } from './Community';
import { Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class CommunityService {

  private communityUrl = "http://localhost:8080/community"

  httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
  
  constructor(private messageService: MessageService, private http: HttpClient, private router: Router) { }

  getCommunity(lat:number, lng:number){
    return this.http.get<Community>(this.communityUrl + "/" + lat + "/" + lng).pipe(tap(), this.handleError<number[]>('getCommunity'));
  }

  private handleError<T>(operation = 'operation', result?: T) {
      return (error: any): Observable<T> => {
        console.error(error);
        this.messageService.add(`${operation} failed: ${error.message}`);
        return of(result as T);
      };
    }
}

