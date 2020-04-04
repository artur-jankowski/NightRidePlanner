import { Component, OnInit } from '@angular/core';
import { GroupService } from '../services/group.service';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-group-overview',
  templateUrl: './group-overview.component.html',
  styleUrls: ['./group-overview.component.css']
})
export class GroupOverviewComponent implements OnInit {

  session: SessionService;
  groupService: GroupService;

  constructor(groupService: GroupService, session: SessionService) {
    this.session = session;
    this.groupService = groupService;
  }

  ngOnInit(): void {
  }

  join() {
    this.groupService.joinGroup(this.session.loggedUser).subscribe((result: boolean) => { console.log(result); });
  }

}