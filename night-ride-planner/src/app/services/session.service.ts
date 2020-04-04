import { Injectable } from '@angular/core';
import { User } from '../model/user';

@Injectable({
  providedIn: 'root'
})
export class SessionService {

  loggedUser: User = new User();
  isLogged: boolean = false;
  constructor() { }

  public login(user: User) {
    this.loggedUser = user;
    console.log("Logged as user");
    console.log(user);
    this.isLogged = true;
  }

  public logout() {
    this.loggedUser = new User();
    this.isLogged = false;
  }
}
