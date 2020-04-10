import { Component, OnInit, Input, OnChanges, OnDestroy } from '@angular/core';
import { GroupService } from '../services/group.service';
import { SessionService } from '../services/session.service';
import { Group } from '../model/group';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-group-overview',
  templateUrl: './group-overview.component.html',
  styleUrls: ['./group-overview.component.css']
})
export class GroupOverviewComponent implements OnInit, OnDestroy {

  _subscription: Subscription;
  currentGroup: Group;
  test: boolean;

  constructor(public groupService: GroupService, public session: SessionService) {
    this._subscription = groupService.groupChange.subscribe((value) => {
      this.currentGroup = value;
      this.test = this.containsUser();
    });
  }
  ngOnInit(): void {
    this.test = this.containsUser();
  }
  ngOnDestroy(): void {
    this._subscription.unsubscribe;
  }
  join() {
    this.groupService.joinGroup().subscribe((result: boolean) => { console.log(result); });
  }

  containsUser() {
    console.log(this.groupService.currentGroup);
    return this.groupService.currentGroup.usersInGroup.some(user => user.username === this.session.currentUser.username);
  }

  leave() {
    this.groupService.leaveGroup();
  }

}
