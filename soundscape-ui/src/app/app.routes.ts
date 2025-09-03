import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { RegisterComponent } from './register/register.component';
import { AccountComponent } from './account/account.component';
import { FollowersComponent } from './followers/followers.component';
import { FollowingComponent } from './following/following.component';
import { SongsComponent } from './songs/songs.component';
import { SpotifyRegisterComponent } from './spotify-register/spotify-register.component';
import { AlbumTracksComponent } from './album-tracks/album-tracks.component';
import { SearchProfilesComponent } from './search-profiles/search-profiles.component';
import { ViewAccountComponent } from './view-account/view-account.component';


export const routes: Routes = [
  { path: 'login', component: LoginComponent},
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'main-menu', component: MainMenuComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'account', component: AccountComponent},
  {path: 'followers', component: FollowersComponent},
  {path: 'following', component: FollowingComponent},
  {path: 'songs', component: SongsComponent},
  {path: 'spotify-register', component: SpotifyRegisterComponent},
  {path: 'album-tracks', component: AlbumTracksComponent},
  {path: 'search-profiles', component: SearchProfilesComponent},
  {path: 'profile', component: ViewAccountComponent}
];