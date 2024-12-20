import { Component } from '@angular/core';
import { UserService } from '../user.service'
import { Router } from '@angular/router';
@Component({
  selector: 'app-welcome-menu',
  templateUrl: './welcome-menu.component.html',
  styleUrl: './welcome-menu.component.css'
})
export class WelcomeMenuComponent {

constructor(private router: Router) {
      }
    navigate(dest : string){
      this.router.navigate([dest]);
      }
}
