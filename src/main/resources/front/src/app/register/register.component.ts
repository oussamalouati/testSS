import { Component } from '@angular/core';
import { UserService } from '../user.service'
import { Appuser } from '../appuser'
@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {
  user = new Appuser();
  signUpMessage : string | null = null;
  roles = {
      ROLE_CLIENT: false,
      ROLE_VENDEUR: false,
      ROLE_ADMIN: false
    };
  constructor(private userService: UserService) {}

  register() {
    this.user.roles =  this.getSelectedRoles();
      this.userService.save(this.user).subscribe({
        next: (response) => {
          this.signUpMessage = response.message;
        },
      error: (error) => {
        this.signUpMessage = error;
        },
       });
    }
    getSelectedRoles(): string[] {
      return (Object.keys(this.roles) as (keyof typeof this.roles)[])
          .filter((role) => this.roles[role]);

    }
}
