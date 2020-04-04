import { Component, OnInit, SystemJsNgModuleLoaderConfig } from '@angular/core';
import { GroupService } from '../services/group.service';
import { Group } from '../model/group';

@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent implements OnInit {
  groupService: GroupService;
  mGroup: Group = new Group();

  constructor(groupService: GroupService) { 
    this.groupService = groupService;
  }

  ngOnInit(): void {
  }
  
  onSubmit() {
    this.groupService.createGroup(this.mGroup).subscribe((id:number) => console.log("You've created a group - id: "+ id));
  }

}
