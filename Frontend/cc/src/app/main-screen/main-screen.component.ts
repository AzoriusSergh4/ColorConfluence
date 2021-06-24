import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {tdBounceAnimation, tdPulseAnimation} from '@covalent/core/common';
import {Router} from '@angular/router';
import {CardService} from '../services/card.service';
import {BaseComponent} from '../base/base.component';
import {DeckService} from '../services/deck.service';
import {Deck} from '../deck/deck.component';
import {UtilsService} from '../services/utils.service';

@Component({
  selector: 'cc-main-screen',
  templateUrl: './main-screen.component.html',
  styleUrls: ['./main-screen.component.css'],
  animations: [
    tdBounceAnimation,
    tdPulseAnimation
  ]
})
export class MainScreenComponent extends BaseComponent implements OnInit {
  pulseStateCards = false;
  pulseStateDeck = false;

  loadingCards: boolean;
  loadingDecks: boolean;

  cards: any[];

  decks: Deck[];

  cardName = new FormControl('');

  constructor(protected router: Router, private cardService: CardService, private deckService: DeckService, public utilsService: UtilsService) {
    super(router);
    this.loadingCards = true;
    this.loadingDecks = true;
    this.deckService.getAllDecks(0).subscribe(response => {
      this.decks = response.content;
      this.loadingDecks = false;
    }, error => {
      console.error(error);
      this.loadingDecks = false;
    });
    this.cardService.getCardPopularity(0).subscribe(response => {
      this.cards = response.content;
      this.loadingCards = false;
    }, error => {
      console.log(error);
      this.loadingCards = false;
    });
  }

  onCardSubmit(): void {
    if (this.cardName.value !== '') {
      this.cardService.countCardsByName(this.cardName.value).subscribe(cards => {
        if (cards === 1) {
          this.cardService.getCardsByName(this.cardName.value).subscribe(card => this.router.navigate(['/card/' + card.content[0].id]), error => console.error(error));
        } else {
          this.router.navigate(['/cards'], {queryParams: {name: this.cardName.value}});
        }
      }, error => console.error(error));
    }
  }

  ngOnInit(): void {
  }

}
