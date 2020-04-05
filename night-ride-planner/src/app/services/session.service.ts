import { Injectable } from '@angular/core';
import { User } from '../model/user';
import * as jwt_decode from 'jwt-decode';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  token: string = "";
  isLogged: boolean = false;
  currentUser: User = null;
  constructor() { }

  public login(token: string) {
    this.token = token;
    if (token.length > 8) {
      this.isLogged = true;
    }
    var decoded = jwt_decode(token);
    console.log(decoded);
    this.currentUser = new User();
    this.currentUser.username = decoded.sub;
  }

  public logout() {
    this.token = "";
    this.isLogged = false;
  }
}
