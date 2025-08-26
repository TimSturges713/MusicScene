import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { catchError, Observable, of, tap } from 'rxjs';
import { MessageService } from './message.service';
import { UserService } from './user.service';
import { User } from './user';

@Injectable({
  providedIn: 'root'
})
export class SpotifyService {

  httpOptions = {
      headers: new HttpHeaders({ 'Content-Type': 'application/json' })
    };

  constructor(private http: HttpClient, private messageService: MessageService, private userService: UserService) { }

  requestAccountAccessRegister(){
    return this.http.get<{url:string}>('http://localhost:8080/spotify/login?username=' + localStorage.getItem('username') + '&register=true')
      .pipe(tap(_ => {this.messageService.add("Connecting to Spotify...")})
      ,catchError(this.handleError<{url:string}>('requestAccountAccess()')))
  }

  requestAccountAccessChange(){
    return this.http.get<{url:string}>('http://localhost:8080/spotify/login?username=' + localStorage.getItem('username') + '&register=false')
      .pipe(tap(_ => {this.messageService.add("Connecting to Spotify...")})
      ,catchError(this.handleError<{url:string}>('requestAccountAccess()')))
  }

  getAlbums(user: User){
      return this.http.get<{albums: string}>("http://localhost:8080/spotify/artist-albums?username=" + localStorage.getItem('username')).pipe(
        tap(_=>{this.messageService.add("Getting albums...")}),
        catchError(this.handleError<{albums: string}>('getAlbums()'))
      )
    
  }

  /**
     * Handles HTTP operation failures.
     * Logs the error and allows the app to continue by returning an empty result.
     * @param operation - Name of the failed operation.
     * @param result - Optional default result to return.
     */
    private handleError<T>(operation = 'operation', result?: T) {
      return (error: any): Observable<T> => {
        console.error(error);
        this.messageService.add(`${operation} failed: ${error.message}`);
        return of(result as T);
      };
    }
}
