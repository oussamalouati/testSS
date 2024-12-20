export class Appuser {
         id?: number;
         firstName?: string;
         lastName?: string;
         email?: string;
         password?: string;
         roles : string[];
         constructor(id?: number, firstName?: string, lastName?: string, email?: string, password?: string, roles?:string[]) {
             this.id = id;
             this.firstName = firstName;
             this.lastName = lastName;
             this.email = email;
             this.password = password;
             this.roles = roles ?? [];
         }
}
