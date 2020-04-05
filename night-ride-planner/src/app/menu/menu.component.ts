import { Component, OnInit } from '@angular/core';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  sessionService: SessionService;

  constructor(sessionService: SessionService) {
    this.sessionService = sessionService
  }

  ngOnInit(): void {
  }

  logout() {
    this.sessionService.logout();
  }

  openGroup() {

  }

}
