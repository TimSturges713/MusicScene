/**
 * The injectable methods that can be used to handle the majority of user
 * functionality, including authentication and signing in. 
 * 
 * @author Timothy Sturges
 * @filename user.service.ts
 */

import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { User } from './user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { MessageService } from './message.service';
import { urlToHttpOptions } from 'url';


@Injectable({
  providedIn: 'root'
})
export class UserService {

  private loginUrl = "http://localhost:8080/users/"

  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient, private log: MessageService) { }

  /**
   * Login handles the authentication of an entered username and password
   * to enter an account into the website. Uses getUser() to handle HTTP
   * requests.
   * @param username The username of the requested user to log in to
   * @param password The password of the requested user to log in to
   */
  login(username:string, password:string){
    console.log(username);
    
    console.log(password);
    this.getUser(username).subscribe(user => {
      if(user == null){
        this.log.add("User not found");
        return;
      }
      if(user.password != password){
        this.log.add("Incorrect password");
        return;
      }
      console.log("Logged in");
      console.log(username);
      localStorage.setItem("username", username);
      this.log.add("You have been logged in");
    });
  }

  /**
   * Handles HTTP request to get a user via username entered via the user.
   * @param username - The username of the requested user.
   * @return  A User if found, or an error message if not found.
   */
  getUser(username:string):Observable<User>{
    return this.http.get<User>(this.loginUrl + username).pipe(
      tap(_ => this.log.add(`fetched user username=${username}`)),
      catchError(this.handleError<User>(`getUser username=${username}`))
    );
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
      this.log.add(`${operation} failed: ${error.message}`);
      return of(result as T);
    };
  }
}
