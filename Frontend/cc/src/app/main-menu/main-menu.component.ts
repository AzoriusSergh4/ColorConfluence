import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';
import {LoginService} from '../services/login.service';
import {BaseComponent} from '../base/base.component';

@Component({
  selector: 'cc-main-menu',
  templateUrl: './main-menu.component.html',
  styleUrls: ['./main-menu.component.css']
})
export class MainMenuComponent extends BaseComponent implements OnInit {

  constructor(protected router: Router, public loginService: LoginService) {
    super(router);
  }

  ngOnInit(): void {
  }

  goToMain(): void {
    this.router.navigate(['/']);
  }

  goToProfile(): void {
    this.router.navigate(['profile']);
  }

  logout() {
    this.loginService.logout();
    this.redirectToHome();
  }

}
