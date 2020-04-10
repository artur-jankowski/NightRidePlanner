import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { Group } from '../model/group';
import { GroupService } from '../services/group.service';
import { group } from '@angular/animations';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {

  groups: Array<Group>;

  constructor(private groupService: GroupService) {
    this.groupService.listGroups().subscribe((g: Array<Group>) => { this.groups = g; });
  }

  ngOnInit(): void {

    this.groupService.listGroups().subscribe((g: Array<Group>) => { this.groups = g; console.log(g); });
  }

  openGroupDetails(clickedId: any) {
    this.groupService.updateCurrent(this.groups.find(g => g.id == clickedId));
  }

}
