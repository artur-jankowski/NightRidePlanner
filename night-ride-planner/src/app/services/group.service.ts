import { Injectable } from '@angular/core';
import { Group } from '../model/group';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';
import { JoinGroupRequest } from '../model/join-group-request';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  currentGroup: Group = new Group();
  constructor(private http: HttpClient) {
    this.http = http;
  }

  backendUrl = 'http://localhost:8080/NightRidePlanner/group';

  public listGroups() {
    return this.http.get(this.backendUrl + "/all");
  }

  public createGroup(model: Group) {
    return this.http.post(this.backendUrl + "/create", model);
  }

  public joinGroup(user: User) {
    let requestBody: JoinGroupRequest = new JoinGroupRequest(user, this.currentGroup);
    return this.http.post(this.backendUrl + "/join", requestBody);
  }
}
