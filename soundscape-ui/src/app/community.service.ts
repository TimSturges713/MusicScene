import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Community } from './Community';
import { catchError, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';

@Injectable({
  providedIn: 'root'
})
export class CommunityService {

  private communityUrl = "http://localhost:8080/community"

  httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };
  
  constructor(private messageService: MessageService, private http: HttpClient) { }

  getCommunity(name:string): Observable<Community>{
    return this.http.get<Community>(this.communityUrl + "/" + name).pipe(tap(_ => {}), catchError(this.handleError<Community>('getCommunity')));
  }

  private handleError<T>(operation = 'operation', result?: T) {
      return (error: any): Observable<T> => {
        console.error(error);
        this.messageService.add(`${operation} failed: ${error.message}`);
        return of(result as T);
      };
    }
}

