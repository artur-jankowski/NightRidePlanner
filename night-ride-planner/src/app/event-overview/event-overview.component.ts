import { Component, OnInit } from '@angular/core';
import { Subscription } from 'rxjs';
import { EventService } from '../services/event.service';
import { Event } from '../model/event'
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-event-overview',
  templateUrl: './event-overview.component.html',
  styleUrls: ['./event-overview.component.css']
})

export class EventOverviewComponent implements OnInit {

  _subscription: Subscription;
  currentEvent: Event;
  test: boolean;

  constructor(public eventService: EventService, public session: SessionService) {
    this._subscription = eventService.eventChange.subscribe((value) => {
      this.currentEvent = value;
      this.test = this.containsUser();
    });
  }

  ngOnInit(): void {
    this.test = this.containsUser();
  }
  ngOnDestroy(): void {
    this._subscription.unsubscribe();
  }

  containsUser() {
    console.log(this.eventService.currentEvent);
    return this.eventService.currentEvent.attendants.some(user => user.username === this.session.currentUser.username);
  }

  join() {
    this.eventService.joinEvent();
    this.ngOnInit();
  }
  leave() {
    this.eventService.leaveEvent();
    this.ngOnInit();
  }

}
