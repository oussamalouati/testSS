import { Injectable } from '@angular/core';
import { HttpClient,HttpHeaders,HttpResponse  } from "@angular/common/http";
import { Router } from '@angular/router';
import { Appuser } from './appuser';
import { ResponseMessage } from './responseMessage';
import { Observable , throwError } from 'rxjs';
import { catchError, map } from 'rxjs/operators';
@Injectable({
  providedIn: 'root'
})
export class UserService {
   roles: string[] =[];
  private baseUrl: string;
  private responseMessage = new ResponseMessage();

      constructor(public http: HttpClient, private router: Router) {
        console.log('MyService instance created');
        this.baseUrl = 'http://localhost:8080';
      }

  public login(user: Appuser): Observable<{ message: string }> {
    return this.http.post<{message: string, roles: string[]}>(`${this.baseUrl}/req/login`, user, { observe: 'response' }).pipe(
      map((response) => {
        const responseBody = response.body;
        if (!responseBody) {
          throw new Error('No response body found');
        }
        this.setRoles(response.body.roles);
        console.log('Login successful', response);
        this.router.navigate(['/welcome']);
        return { message: responseBody.message }; // Type assertion ensures correct typing
      }),
      catchError((error) => {
        console.error('Login failed', error);
        const errorMessage = error.error || 'An error occurred during login';
        return throwError(() => new Error(errorMessage));
      })
    );
  }

  public save(user: Appuser): Observable<{ message: string }> {
    return this.http.post<{ message: string }>(`${this.baseUrl}/register`, user, { observe: 'response' }).pipe(
      map((response) => {
        console.log('signUp successful', response);
        alert("signUp successful");
        this.router.navigate(['/login']);
        return { message: 'signUp successful' };
      }),
      catchError((error) => {
        console.error('signUp failed', error);
        const errorMessage = error.error;
        return throwError(() => new Error(errorMessage));
      })
    );
  }

public logout() {
      this.http.post(`${this.baseUrl}/logout`,{ observe: 'response' }).subscribe({
            next: (response) => {
              console.log('logout successful', response);
              this.router.navigate(['/login']);
            },
            error: (error) => {
              console.error('logout failed', error);
            },
          });
    }

  public setRoles(roles: string[]): void {

    this.roles = roles;
  }

  public getRoles(): string[] {
    return this.roles;
  }

  public isLoggedIn(): boolean {
    return !!this.roles?.length;
  }

  public hasRole(role: string): boolean {
    if(this.roles){
      return this.roles?.includes(role);
      }
    return false;
  }

}
