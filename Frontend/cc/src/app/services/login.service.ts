import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import {CommonService} from './common.service';
import {catchError, map} from 'rxjs/operators';

const USERS_URL = '/users';
const LOGIN_URL = USERS_URL + '/login';
const LOGOUT_URL = USERS_URL + '/logout';
const REGISTRATION_URL = USERS_URL + '/registration';
const ACCOUNT_CONFIRMATION_URL = USERS_URL + '/account/confirmation';
const PASSWORD_URL = USERS_URL + '/password';
const PASSWORD_CHANGE_URL = PASSWORD_URL + '/change';
const PASSWORD_RECOVERY_URL = PASSWORD_URL + '/recovery';
const PASSWORD_RECOVERY_CONFIRMATION_URL = PASSWORD_RECOVERY_URL + '/confirmation';


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
    return this.apiGetRequest(LOGIN_URL, headers).pipe(
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
    this.apiGetRequest(LOGOUT_URL).subscribe(response => {
      this.removeCurrentUser();
      return response;
    }, error => {
      console.error(error);
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
    return this.apiPostRequest(REGISTRATION_URL, body, headers);
  }

  confirmAccount(tk: string) {
    return this.apiGetRequest(ACCOUNT_CONFIRMATION_URL + '?tk=' + tk);
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
    return this.apiPostRequest(PASSWORD_CHANGE_URL, body, headers).pipe(
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
    return this.apiPostRequest(PASSWORD_RECOVERY_CONFIRMATION_URL + '?tk=' + tk, body, headers);
  }

  resetPassword(email: string) {
    return this.apiGetRequest(PASSWORD_RECOVERY_URL + '?email=' + email);
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
