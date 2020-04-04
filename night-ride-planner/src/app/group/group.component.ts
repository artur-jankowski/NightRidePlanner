import { Component, OnInit } from '@angular/core';
import { SessionService } from '../services/session.service';
import { GroupService } from '../services/group.service';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {

  session: SessionService;
  groupService: GroupService;
  createMode: boolean = false;
  createLabel: string = "Create group";

  constructor(session: SessionService, groupService: GroupService) {
    this.session = session;
    this.groupService = groupService;
  }

  ngOnInit(): void {
  }

  toggleCreate() {
    if (this.createMode) {
      this.createLabel = "Create group";
      this.createMode = false;
    } else {
      this.createLabel = "Hide form";
      this.createMode = true;
    }
  }

}
