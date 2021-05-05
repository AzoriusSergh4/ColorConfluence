import { Component, OnInit } from '@angular/core';
import {FormGroup} from '@angular/forms';
import {LoginService} from '../services/login.service';
import {MatSnackBar} from '@angular/material/snack-bar';
import {ActivatedRoute, Router} from '@angular/router';
import {BaseComponent} from '../base/base.component';

@Component({
  selector: 'cc-recover-password',
  templateUrl: './recover-password.component.html',
  styleUrls: ['./recover-password.component.css']
})
export class RecoverPasswordComponent extends BaseComponent implements OnInit {

  tk: string;
  constructor(protected router: Router, private snackBar: MatSnackBar, public loginService: LoginService, private route: ActivatedRoute) {
    super(router);
    this.route.queryParams.subscribe(params => {
      this.tk = params.tk;
      if (!this.tk || loginService.isLogged){
        this.redirectToHome();
      }
    });
  }

  ngOnInit(): void {
  }

  recover(fg: FormGroup) {
    this.loginService.changeResetPassword(fg.get('oldPassword').value, fg.get('password').value, this.tk).subscribe(response => {
      this.snackBar.open('You have changed your password successfully!', 'OK');
      this.redirectToHome();
    }, error => {
      this.snackBar.open('There was an error changing your password. Please try again', 'OK');
    });
  }
}
