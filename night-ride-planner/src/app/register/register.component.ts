import { Component, OnInit, ɵɵcontainerRefreshStart } from '@angular/core';
import { User } from '../model/user';
import { UserService } from '../services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css']
})
export class RegisterComponent implements OnInit {

  constructor(userService: UserService) {
    this.userService = userService;
   }

  mUser: User = new User;
  userService: UserService;
  ngOnInit(): void {
  }

  onSubmit() {
    console.log("User tries to register")
    this.userService.register(this.mUser.username, this.mUser.password).subscribe((user:User) => {console.log(user)});
  }
  
}
