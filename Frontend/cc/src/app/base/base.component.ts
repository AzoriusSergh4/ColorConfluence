import { Component, OnInit } from '@angular/core';
import {Router} from '@angular/router';
import {Deck} from '../deck/deck.component';
import {DeckService} from '../services/deck.service';

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

}
