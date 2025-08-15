import { Injectable } from '@angular/core';
import { Observable, of } from 'rxjs';
import { User } from './user';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { catchError, tap } from 'rxjs/operators';
import { MessageService } from './message.service';
import { Router, UrlSegmentGroup } from '@angular/router';
import { UserService} from './user.service';
import { Following } from './following';

@Injectable({
  providedIn: 'root'
})
export class FollowingService {

  private followUrl = "http://localhost:8080/users/following";


  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };

  constructor(private http: HttpClient, private messageService: MessageService, private router: Router, private userService: UserService) { }

  followUser(follower: number, followed: number){
    const follow : Following= {
      followingId: follower,
      followedId: followed
    }
    return this.http.post<Following>(this.followUrl, follow, this.httpOptions).pipe(tap(_ => this.messageService.add(`Followed User Successfully`)),
      catchError(this.handleError<Following>('followUser')));
  }

  deleteUserRecords(user: User){
    if(user == null){
      this.messageService.add("User not found");
      return;
    }
    var userId = user.userId as number;
    return this.http.delete<Following>('http://localhost:8080/users/following/' + userId).subscribe();
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
