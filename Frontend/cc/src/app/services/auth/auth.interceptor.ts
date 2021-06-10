import {Injectable} from '@angular/core';
import {HttpEvent, HttpHandler, HttpInterceptor, HttpRequest} from '@angular/common/http';
import {Observable} from 'rxjs';
import {LoginService} from '../login.service';

@Injectable()
export class BasicAuthInterceptor implements HttpInterceptor {

  constructor(private loginService: LoginService) {}

  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

    // add authorization header with basic auth credentials if available
    const user = JSON.parse(this.loginService.isPermanentSesion ? localStorage.getItem('ccUser') : sessionStorage.getItem('ccUser'));

    if (user && user.authdata) {
      request = request.clone({
        setHeaders: {
          Authorization: `Basic ${user.authdata}`,
        }
      });
    }

    return next.handle(request);
  }
}
