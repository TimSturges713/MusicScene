import { Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { MainMenuComponent } from './main-menu/main-menu.component';
import { RegisterComponent } from './register/register.component';
import { AccountComponent } from './account/account.component';
import { FollowersComponent } from './followers/followers.component';


export const routes: Routes = [
  { path: 'login', component: LoginComponent},
  {path: '', redirectTo: '/login', pathMatch: 'full'},
  {path: 'main-menu', component: MainMenuComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'account', component: AccountComponent},
  {path: 'followers', component: FollowersComponent}
];