import { Injectable } from '@angular/core';
import {CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot,Router} from '@angular/router';
import { UserService } from './user.service'

@Injectable({
  providedIn: 'root',
})
export class RoleGuard implements CanActivate {
  constructor(private userService: UserService, private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const expectedRoles = route.data['roles'] as string[];

    if (this.userService.isLoggedIn() && expectedRoles.some(role => this.userService.hasRole(role))) {
      return true;
    }

    this.router.navigate(['/unauthorized']);
    return false;
  }

}
