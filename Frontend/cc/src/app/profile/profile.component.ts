import {Component, OnInit} from '@angular/core';
import {LoginService} from '../services/login.service';
import {BaseComponent} from '../base/base.component';
import {Router} from '@angular/router';
import {FormGroup} from '@angular/forms';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'cc-profile',
  templateUrl: './profile.component.html',
  styleUrls: ['./profile.component.css']
})
export class ProfileComponent extends BaseComponent implements OnInit {

  constructor(private snackBar: MatSnackBar, public loginService: LoginService, protected router: Router) {
    super(router);
    if (!loginService.isLogged) {
      this.redirectToHome();
    }
  }

  ngOnInit(): void {
  }

  changePassword(fg: FormGroup) {
    this.loginService.changePassword(fg.get('oldPassword').value, fg.get('password').value).subscribe(response => {
      this.snackBar.open('You have changed your password successfully!', 'OK');
    }, error => {
      this.snackBar.open('There was an error chaning your password. Please try again', 'OK');
    });
  }
}
