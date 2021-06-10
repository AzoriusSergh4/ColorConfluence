import {Component, OnInit} from '@angular/core';
import {LoginService} from '../services/login.service';
import {FormBuilder, FormGroup, Validators} from '@angular/forms';
import {Router} from '@angular/router';
import {BaseComponent} from '../base/base.component';
import {MatSnackBar} from '@angular/material/snack-bar';

@Component({
  selector: 'cc-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent extends BaseComponent implements OnInit {

  loginForm: FormGroup;
  resetForm: FormGroup;
  notFound: boolean;

  resetHidden: boolean;

  constructor(private fb: FormBuilder, public loginService: LoginService, protected router: Router, private snackBar: MatSnackBar) {
    super(router);
    if (loginService.isLogged) {
      this.redirectToHome();
    }else {
      this.loginForm = this.fb.group({
        username: ['', [Validators.required]],
        password: ['', [Validators.required]],
        permanentSession: ''
      });
      this.resetForm = this.fb.group({
        email: ['', [Validators.email]]
      });
      this.resetHidden = true;
    }
  }

  ngOnInit(): void {
  }

  login(): void {
    this.loginService.login(this.loginForm.get('username').value, this.loginForm.get('password').value, this.loginForm.get('permanentSession').value).subscribe(() => {
        this.redirectToHome();
      }, error => {
        console.error(error);
        this.notFound = true;
      }
    );
  }

  resetPassword(): void {
    this.loginService.resetPassword(this.resetForm.get('email').value).subscribe(r => {
      this.snackBar.open('An email has been sent', 'OK', {duration: 2000});
    });
  }
}
