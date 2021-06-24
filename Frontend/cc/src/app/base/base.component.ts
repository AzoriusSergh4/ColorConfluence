import {Component, OnInit} from '@angular/core';
import {Router} from '@angular/router';

@Component({
  selector: 'app-base',
  template: ``,
  styleUrls: ['./base.component.css']
})
export class BaseComponent implements OnInit {

  constructor(protected router: Router) {
  }

  ngOnInit(): void {
  }

  redirectToHome() {
    this.router.navigate(['']);
  }

  goToDecks(): void {
    this.router.navigate(['decks']);
  }

  goToCards(): void {
    this.router.navigate(['cards']);
  }

  goToMyDecks(): void {
    this.router.navigate(['user-decks']);
  }

}
