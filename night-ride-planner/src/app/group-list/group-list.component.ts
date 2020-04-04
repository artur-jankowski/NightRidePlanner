import { Component, OnInit } from '@angular/core';
import { Group } from '../model/group';
import { GroupService } from '../services/group.service';
import { group } from '@angular/animations';

@Component({
  selector: 'app-group-list',
  templateUrl: './group-list.component.html',
  styleUrls: ['./group-list.component.css']
})
export class GroupListComponent implements OnInit {

  groupService: GroupService;
  groups: Array<Group>;

  constructor(groupService: GroupService) {
    this.groupService = groupService;
    this.groupService.listGroups().subscribe((g: Array<Group>) => { this.groups = g; });
  }

  ngOnInit(): void {
    this.groupService.listGroups().subscribe((g: Array<Group>) => { this.groups = g; console.log(g); });
    console.log(this.groups);
  }

  openGroupDetails(clickedId: any) {
    this.groupService.currentGroup = this.groups.find(g => g.id == clickedId);
  }

}
