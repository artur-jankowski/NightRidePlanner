import { Component, OnInit } from '@angular/core';
import { SessionService } from '../services/session.service';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  constructor(public sessionService: SessionService) {
  }

  ngOnInit(): void {
  }

  logout() {
    this.sessionService.logout();
  }
}
