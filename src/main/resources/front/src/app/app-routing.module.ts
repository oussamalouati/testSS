import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { WelcomeMenuComponent } from './welcome-menu/welcome-menu.component';
import { UnauthorizedComponent } from './unauthorized/unauthorized.component';
import { ClientComponent } from './client/client.component';
import { VendeurComponent } from './vendeur/vendeur.component';
import { RoleGuard } from './role.guard';

const routes: Routes = [
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'client', component: ClientComponent , canActivate: [RoleGuard], data: { roles: ['ROLE_CLIENT','ROLE_ADMIN'] },},
  { path: 'vendeur', component: VendeurComponent , canActivate: [RoleGuard], data: { roles: ['ROLE_VENDEUR','ROLE_ADMIN'], }},
  { path: 'welcome', component: WelcomeMenuComponent , canActivate: [RoleGuard], data: { roles: ['ROLE_CLIENT','ROLE_ADMIN','ROLE_VENDEUR'], }},
  { path: 'unauthorized', component: UnauthorizedComponent },
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  ];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
