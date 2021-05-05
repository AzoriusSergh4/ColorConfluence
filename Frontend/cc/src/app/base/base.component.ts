import { Component, OnInit } from '@angular/core';
import {LoginService} from '../services/login.service';
import {Router} from '@angular/router';

@Component({
  selector: 'app-base',
  template: ``,
  styleUrls: ['./base.component.css']
})
export class BaseComponent implements OnInit {

  constructor(protected router: Router) { }

  ngOnInit(): void {
  }

  redirectToHome() {
    this.router.navigate(['']);
  }

}
