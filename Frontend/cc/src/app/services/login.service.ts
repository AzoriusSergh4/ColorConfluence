import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CCError, CommonService} from './common.service';
import {catchError, map} from 'rxjs/operators';

const USER_URL = '/user';

export interface User {
  id?: number;
  username: string;
  email: string;
  authdata: string;
}

export interface RegisterForm {
  email: string;
  username: string;
  password: string;
}

export interface ChangePasswordForm {
  oldPassword: string;
  newPassword: string;
}

@Injectable({
  providedIn: 'root'
})
export class LoginService extends CommonService {

  isLogged = false;
  isPermanentSesion = false;
  user: User;

  constructor(http: HttpClient) {
    super(http);
    let user = JSON.parse(localStorage.getItem('ccUser'));
    if (user) {
      this.setCurrentUser(user);
      this.isPermanentSesion = true;
    } else {
      user = JSON.parse(this.isPermanentSesion ? localStorage.getItem('ccUser') : sessionStorage.getItem('ccUser'));
      if (user) {
        this.setCurrentUser(user);
        this.isPermanentSesion = false;
      }
    }
  }

  login(user: string, pass: string, permanentSession: boolean) {

    const auth = window.btoa(user + ':' + pass);

    const headers = new HttpHeaders({
      Authorization: 'Basic ' + auth,
      'X-Requested-With': 'XMLHttpRequest',
    });
    return this.apiGetRequest(USER_URL + '/login', headers).pipe(
      map(u => {
        if (u) {
          this.setCurrentUser(u);
          u.authdata = auth;
          this.isPermanentSesion = permanentSession;
          this.isPermanentSesion ? localStorage.setItem('ccUser', JSON.stringify(u)) : sessionStorage.setItem('ccUser', JSON.stringify(u));
        }
        return u;
      })
    );
  }

  logout() {
    this.apiGetRequest(USER_URL + '/logout').subscribe(response => {
      this.removeCurrentUser();
      return response;
    });
  }

  register(user: string, mail: string, pass: string) {
    const registerForm: RegisterForm = {
      email: mail,
      username: user,
      password: pass
    };

    const body = JSON.stringify(registerForm);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.apiPostRequest(USER_URL + '/register', body, headers);
  }

  confirmAccount(tk: string){
    return this.apiGetRequest(USER_URL + '/confirm-account?tk=' + tk);
  }

  changePassword(oldPass: string, newPass: string) {
    const changePasswordForm: ChangePasswordForm = {
      oldPassword: oldPass,
      newPassword: newPass
    };

    const body = JSON.stringify(changePasswordForm);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.apiPostRequest(USER_URL + '/change-password', body, headers).pipe(
      map(response => {
        this.user.authdata = window.btoa(this.user.username + ':' + newPass);
        this.isPermanentSesion ? localStorage.setItem('ccUser', JSON.stringify(this.user)) : sessionStorage.setItem('ccUser', JSON.stringify(this.user));
        return this.user;
      }, catchError(err => {
        return err;
      })
    ));
  }

  changeResetPassword(oldPass: string, newPass: string, tk: string) {
    const changePasswordForm: ChangePasswordForm = {
      oldPassword: oldPass,
      newPassword: newPass
    };

    const body = JSON.stringify(changePasswordForm);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.apiPostRequest(USER_URL + '/confirm-recover-password', body, headers);
  }

  resetPassword(email: string) {
    return this.apiGetRequest(USER_URL + '/recover-password');
  }

  private setCurrentUser(user: User) {
    this.isLogged = true;
    this.user = user;
  }

  removeCurrentUser() {
    this.isPermanentSesion ? localStorage.removeItem('ccUser') : sessionStorage.removeItem('ccUser');
    this.isLogged = false;
    this.isPermanentSesion = false;
  }
}
