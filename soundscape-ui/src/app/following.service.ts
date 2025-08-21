/**
 * All injectable methods that apply to following objects and any related website functionality.
 * 
 * @author Timothy Sturges
 * @filename following.service.ts
 */
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
  // the url for creating a new following object, only used for that instance as of now
  private followUrl = "http://localhost:8080/users/following";

  // formatting info for JSON objects, the format of http communication
  httpOptions = {
    headers: new HttpHeaders({ 'Content-Type': 'application/json' })
  };
  // all the services 
  constructor(private http: HttpClient, private messageService: MessageService, private router: Router, private userService: UserService) { }

  /**
   * Follows a user by creating a following object and putting it in the database.
   * @param follower The userId following the followed (current logged in user).
   * @param followed The userId of the user that's now being followed.
   * @returns An observable that shows the status of the httpResponse.
   */
  followUser(follower: number, followed: number){
    const follow : Following= {
      followingId: follower,
      followedId: followed
    }
    return this.http.post<Following>(this.followUrl, follow, this.httpOptions).pipe(tap(_ => this.messageService.add(`Followed User Successfully`)));
  }

  /**
   * Get the specified following object via follower and followed IDs. 
   */
  getSpecifiedFollowing(followingId: number, followedId: number){
    return this.http.get<Following>('http://localhost:8080/users/following/object/' + followingId + '/' + followedId).pipe(tap(), catchError(this.handleError<Following>('getSpecifiedFollowing'))
    );
  }

  unfollowUser(followingId: number, followedId: number){
    const follow : Following={
      followingId: followingId,
      followedId: followedId
    }
    return this.http.delete<Following>('http://localhost:8080/users/followers/' + followingId + '/' + followedId).pipe(tap(_ => this.messageService.add(`Unfollowed User Successfully`)), catchError(this.handleError<Following>('unfollowUser')));
  }

  getFollowers(userId: number){
    return this.http.get<number[]>('http://localhost:8080/users/followers/' + userId).pipe(tap(), catchError(this.handleError<number[]>('getFollowers'))
    );
  }

  getFollowing(userId: number){
    return this.http.get<number[]>('http://localhost:8080/users/following/' + userId).pipe(tap(), catchError(this.handleError<number[]>('getFollowing'))
    );
  }

  /**
   * Delete all user records where their UserId appears in following objects, used when deleting an account.
   * @param user the user that we want to wipe off the records.
   * @returns Observable that shows details of the httpResponse.
   */
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
