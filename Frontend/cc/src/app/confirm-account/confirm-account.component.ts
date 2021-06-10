import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {LoginService} from '../services/login.service';

@Component({
  selector: 'cc-confirm-account',
  templateUrl: './confirm-account.component.html',
  styleUrls: ['./confirm-account.component.css']
})
export class ConfirmAccountComponent implements OnInit {

  brokenTk: boolean;

  constructor(private router: Router, private route: ActivatedRoute, public loginService: LoginService) {
    let tk;
    this.route.queryParams.subscribe(params => {
      tk = params.tk;
      if (tk){
        this.loginService.confirmAccount(tk).subscribe(response => this.brokenTk = false, error => {console.log(error); this.brokenTk = true; });
      }
      else{
        this.brokenTk = true;
      }
    });
  }

  ngOnInit(): void {
  }

}
