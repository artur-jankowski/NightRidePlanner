import { Component, OnInit, Input } from '@angular/core';
import { SessionService } from '../services/session.service';
import { GroupService } from '../services/group.service';
import { Group } from '../model/group';

@Component({
  selector: 'app-group',
  templateUrl: './group.component.html',
  styleUrls: ['./group.component.css']
})
export class GroupComponent implements OnInit {

  constructor(private session: SessionService) {
  }

  ngOnInit(): void {
  }
}
