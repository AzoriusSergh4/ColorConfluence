import {Component, OnInit} from '@angular/core';
import {FormControl} from '@angular/forms';
import {tdBounceAnimation, tdPulseAnimation} from '@covalent/core/common';
import {Router} from '@angular/router';

export interface Card {
  name: string;
  colors: string;
  collection: string;
}

export interface Deck {
  name: string;
  colors: string;
  labels: string[];
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
export class MainScreenComponent implements OnInit {
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

  decks: Deck[] = [
    { name: 'Rakdos aristocrats', colors: '{B}{R}', labels: ['Aggro', 'Combo', 'Sacrifice']},
    { name: 'Azorius Control', colors: '{W}{U}', labels: ['Control']},
    { name: 'Izzet Drakes', colors: '{U}{R}', labels: ['Midrange', 'Tribal']},
    { name: 'Gruul Midrange', colors: '{R}{G}', labels: ['Aggro', 'Midrange']},
    { name: 'Gruul Midrange', colors: '{R}{G}', labels: ['Aggro', 'Midrange']},
    { name: 'Gruul Midrange', colors: '{R}{G}', labels: ['Aggro', 'Midrange']},
    { name: 'Gruul Midrange', colors: '{R}{G}', labels: ['Aggro', 'Midrange']},
  ];

  cardName = new FormControl('');

  constructor(private router: Router) { }

  onCardSubmit(): void {
    if (this.cardName.value !== ''){
      this.router.navigate(['/cards'], {queryParams: {name: this.cardName.value}});
    }
  }

  ngOnInit(): void {
  }

  goToCard(): void {

  }
// TODO implementar redirección al mazo
  goToDeck(): void {

  }
}
