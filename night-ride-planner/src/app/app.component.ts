import { Component, ViewChild } from '@angular/core';
import { SessionService } from './services/session.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'night-ride-planner';
  session: SessionService;
  constructor(session: SessionService){
    this.session = session;
  }
}
