import { Injectable } from '@angular/core';
import { Event } from '../model/event';
import { HttpClient } from '@angular/common/http';
import { User } from '../model/user';
import { JoinGroupRequest } from '../model/join-group-request';
import { Subject } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class EventService {

  currentEvent: Event = new Event();
  eventChange: Subject<Event> = new Subject<Event>();
  eventUpdated: Subject<Event> = new Subject<Event>();
  backendUrl = 'http://localhost:8080/event';

  constructor(private http: HttpClient) {
    this.http = http;
  }

  joinEvent() {
    let resultValue;
    this.http.post(this.backendUrl + "/" + this.currentEvent.id + "/join", null).subscribe((result: boolean) => { console.log(result); resultValue = result });
    this.eventUpdated.next(this.currentEvent);
    return resultValue;
  }

  public leaveEvent() {
    this.eventUpdated.next(this.currentEvent);
    return this.http.post(this.backendUrl + "/" + this.currentEvent.id + "/leave", null);
  }

  public listEvents() {
    return this.http.get(this.backendUrl + "/all");
  }

  public updateCurrent(event: Event): void {
    this.currentEvent = event;
    this.eventChange.next(event);
  }

}
