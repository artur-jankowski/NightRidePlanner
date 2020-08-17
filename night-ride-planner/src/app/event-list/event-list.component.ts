import { Component, OnInit, Output, EventEmitter, OnDestroy } from '@angular/core';
import { Event } from '../model/event';
import { EventService } from '../services/event.service';
import { Subscription } from 'rxjs';

@Component({
  selector: 'app-event-list',
  templateUrl: './event-list.component.html',
  styleUrls: ['./event-list.component.css']
})
export class EventListComponent implements OnInit, OnDestroy {

  events: Array<Event>;
  _subscriber: Subscription;

  constructor(private eventService: EventService) {
    this.eventService.listEvents().subscribe((g: Array<Event>) => { this.events = g; });
    this.eventService.eventUpdated.subscribe(() => this.listEvents());
  }

  ngOnInit(): void {

    this.listEvents();
  }

  listEvents(): void {
    this.eventService.listEvents().subscribe((g: Array<Event>) => { this.events = g; console.log(g); });
  }

  openEventDetails(clickedId: any) {
    this.eventService.updateCurrent(this.events.find(e => e.id == clickedId));
    console.log("Current event: " + this.eventService.currentEvent.name);
  }

  ngOnDestroy(): void {
    if (this._subscriber) {
      this._subscriber.unsubscribe();
    }
  }
}
