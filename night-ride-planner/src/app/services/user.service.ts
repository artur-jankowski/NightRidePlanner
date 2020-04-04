import { Injectable } from "@angular/core";
import { HttpClient } from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  constructor(private http: HttpClient) {
    this.http = http;
  }

  backendUrl = 'http://localhost:8080/NightRidePlanner/';

  public login(username: string, password: string) {
    return this.http.get(this.backendUrl + "/login?username=" + username + "&password=" + password);
  }

  public register(username: string, password: string) {
    return this.http.post(this.backendUrl + "/createUser?username=" + username + "&password=" + password, null);
  }

}