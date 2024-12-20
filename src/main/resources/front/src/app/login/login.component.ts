import { Component } from '@angular/core';
import { UserService } from '../user.service'
import { Appuser } from '../appuser'
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {
  user = new Appuser();
  loginMessage : string | null = null;
constructor(private userService: UserService) {}

  login() {
    this.userService.login(this.user).subscribe({
      next: (response) => {
        this.loginMessage = response.message;
      },
    error: (error) => {
      this.loginMessage = error;
      },
     });
  }

}
