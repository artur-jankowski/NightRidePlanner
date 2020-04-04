import { Injectable } from '@angular/core';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class SessionService {
  token: string = "";
  isLogged: boolean = false;
  constructor() { }

  public login(token: string) {
    this.token = token;
    if (token.length > 8) {
      this.isLogged = true;
    }
  }

  public logout() {
    this.token = "";
    this.isLogged = false;
  }
}
