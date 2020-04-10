import { Component, OnInit, Output, EventEmitter, OnDestroy } from '@angular/core';
import { Group } from '../model/group';
import { GroupService } from '../services/group.service';
import { group } from '@angular/animations';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit, OnDestroy {

  groups: Array<Group>;
  _subscriber: Subscription;

  constructor(private groupService: GroupService) {
    this.groupService.listGroups().subscribe((g: Array<Group>) => { this.groups = g; });
    this.groupService.groupUpdated.subscribe(() => this.listGroups());
  }

  ngOnInit(): void {

    this.listGroups();
  }

  listGroups(): void {
    this.groupService.listGroups().subscribe((g: Array<Group>) => { this.groups = g; console.log(g); });
  }

  openGroupDetails(clickedId: any) {
    this.groupService.updateCurrent(this.groups.find(g => g.id == clickedId));
  }

  ngOnDestroy(): void {
    if (this._subscriber) {
      this._subscriber.unsubscribe();
    }
  }
}
