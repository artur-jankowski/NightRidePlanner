import { Injectable } from '@angular/core';
import { Group } from '../model/group';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';
import { JoinGroupRequest } from '../model/join-group-request';
import { Subject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class GroupService {

  currentGroup: Group = new Group();
  groupChange: Subject<Group> = new Subject<Group>();

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

  public joinGroup() {
    return this.http.post(this.backendUrl + "/" + this.currentGroup.id + "/join", null);
  }

  public leaveGroup() {
    return this.http.post(this.backendUrl + "/" + this.currentGroup.id + "/leave", null);
  }

  public updateCurrent(group: Group): void {
    this.currentGroup = group;
    this.groupChange.next(group);
  }
}
