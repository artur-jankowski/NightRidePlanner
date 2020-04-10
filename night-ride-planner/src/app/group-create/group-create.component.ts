import { Component, OnInit, SystemJsNgModuleLoaderConfig } from '@angular/core';
import { GroupService } from '../services/group.service';
import { Group } from '../model/group';
import { FormBuilder } from '@angular/forms';

@Component({
  selector: 'app-group-create',
  templateUrl: './group-create.component.html',
  styleUrls: ['./group-create.component.css']
})
export class GroupCreateComponent implements OnInit {

  groupSection = this.formBuilder.group({
    name: [''],
    description: [''],
    type: ['']
  })

  constructor(private groupService: GroupService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
  }

  create() {
    this.groupService.createGroup(this.groupSection.value).subscribe((id: number) => console.log("You've created a group - id: " + id));
  }

}
