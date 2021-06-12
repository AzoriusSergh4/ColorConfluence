import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {tdBounceAnimation, tdPulseAnimation} from '@covalent/core/common';
import {Router} from '@angular/router';
import {CardService} from '../services/card.service';
import {BaseComponent} from '../base/base.component';
import {DeckService} from '../services/deck.service';
import {Deck} from '../deck/deck.component';
import {UtilsService} from '../services/utils.service';

export interface Card {
  name: string;
  colors: string;
  collection: string;
}

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

  cards: Card[] = [
    { name: 'Azorius Guildgate', colors: '', collection: 'Ravnica Alleigance' },
    { name: 'Cauldron Familiar', colors: '{B}', collection: 'Throne of Eldraine' },
    { name: 'Kroxa, Titan of Death\'s Hunger', colors: '{B}{R}', collection: 'Theros Beyond Death' },
    { name: 'Kroxa, Titan of Death\'s Hunger', colors: '{B}{R}', collection: 'Theros Beyond Death' },
    { name: 'Kroxa, Titan of Death\'s Hunger', colors: '{B}{R}', collection: 'Theros Beyond Death' },
    { name: 'Kroxa, Titan of Death\'s Hunger', colors: '{B}{R}', collection: 'Theros Beyond Death' },
    { name: 'Kroxa, Titan of Death\'s Hunger', colors: '{B}{R}', collection: 'Theros Beyond Death' },
    { name: 'Kroxa, Titan of Death\'s Hunger', colors: '{B}{R}', collection: 'Theros Beyond Death' },
    { name: 'Kroxa, Titan of Death\'s Hunger', colors: '{B}{R}', collection: 'Theros Beyond Death' },
    { name: 'Kroxa, Titan of Death\'s Hunger', colors: '{B}{R}', collection: 'Theros Beyond Death' },
    { name: 'Kroxa, Titan of Death\'s Hunger', colors: '{B}{R}', collection: 'Theros Beyond Death' },
    { name: 'Kroxa, Titan of Death\'s Hunger', colors: '{B}{R}', collection: 'Theros Beyond Death' }
  ];

  decks: Deck[];

  cardName = new FormControl('');

  constructor(protected router: Router, private cardService: CardService, private deckService: DeckService, public utilsService: UtilsService) {
    super(router);
    this.deckService.getAllDecks(0).subscribe(response => {
      this.decks = response.content;
      console.log(this.decks);
    });
  }

  onCardSubmit(): void {
    if (this.cardName.value !== ''){
      this.cardService.countCardsByName(this.cardName.value).subscribe(cards => {
        if (cards === 1){
          this.cardService.getCardsByName(this.cardName.value).subscribe(card => this.router.navigate(['/card/' + card.content[0].id]), error => console.error(error));
        } else {
          this.router.navigate(['/cards'], {queryParams: {name: this.cardName.value}});
        }
      }, error => console.error(error));
    }
  }

  ngOnInit(): void {
  }

  goToCard(): void {

  }
// TODO implementar redirección al mazo
  goToDeck(id: number): void {
    this.router.navigate([''])
  }
}
