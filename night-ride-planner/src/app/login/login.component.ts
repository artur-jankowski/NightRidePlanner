import { Component, OnInit } from '@angular/core';
import { UserService } from '../services/user.service';
import { SessionService } from '../services/session.service';
import { NgForm } from '@angular/forms';
import { User } from '../model/user';
import { MatDialogModule } from '@angular/material/dialog';
import { AppComponent } from '../app.component';
import { RegisterComponent } from '../register/register.component';


@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  submitted = false;
  public registerMode = false;
  userService: UserService;
  mUser: User = new User();
  sessionService: SessionService;


  constructor(userService: UserService, sessionService: SessionService) {
    this.userService = userService;
    this.sessionService = sessionService;
  }

  ngOnInit(): void {


  }

  onSubmit() {
    console.log(this.mUser);
    console.log("The password is " + this.mUser.password + " and user is " + this.mUser.username);
    this.userService.login(this.mUser.username, this.mUser.password).subscribe((r: boolean) => {
      if (r) {
        this.sessionService.login(this.mUser);
      }
      console.log(r)
    }
    );
  }

  goToRegister() {
    console.log("User clicked register");
    this.registerMode = true;
  }
  goToLogin() {
    console.log("User clicked login");
    this.registerMode = false;
  }
}
