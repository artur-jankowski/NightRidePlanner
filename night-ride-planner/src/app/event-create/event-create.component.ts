import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder } from '@angular/forms';
import { GroupService } from '../services/group.service';

@Component({
  selector: 'app-event-create',
  templateUrl: './event-create.component.html',
  styleUrls: ['./event-create.component.css']
})
export class EventCreateComponent implements OnInit {

  eventSection = this.formBuilder.group({
    name: [''],
    description: [''],
    type: ['']
  })

  constructor(private formBuilder: FormBuilder, public groupService: GroupService) { }

  ngOnInit(): void {

  }

  create() {
    console.log(this.eventSection.value);
    this.groupService.createEvent(this.eventSection.value).subscribe(response => console.log(response));
  }

}
