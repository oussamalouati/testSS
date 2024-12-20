export class ResponseMessage {
         message ?: string;
         roles : string[];
         constructor(message?: string, roles?:string[]) {
             this.message = message;
             this.roles = roles ?? [];
         }
}
