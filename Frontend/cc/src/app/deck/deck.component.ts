import { Component, OnInit } from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {DeckService} from '../services/deck.service';
import {Format} from '../deck-creation/deck-creation.component';
import {User} from '../services/login.service';
import {animate, state, style, transition, trigger} from '@angular/animations';

export interface DeckCard {
  id: number;
  card: any;
  quantity: number;
}

export interface Deck {
  id: number;
  name: string;
  colors: string;
  commanders: DeckCard[];
  main: DeckCard[];
  sideboard: DeckCard[];
  comments: string;
  format: Format[];
  user: User;
}

@Component({
  selector: 'cc-deck',
  templateUrl: './deck.component.html',
  styleUrls: ['./deck.component.css'],
  animations: [
    trigger('detailExpand', [
      state('collapsed', style({height: '0px', minHeight: '0'})),
      state('expanded', style({height: '*'})),
      transition('expanded <=> collapsed', animate('225ms cubic-bezier(0.4, 0.0, 0.2, 1)')),
    ]),
  ],
})
export class DeckComponent implements OnInit {

  deck: any;

  displayedColumns: string[] = ['quantity', 'name', 'cardType', 'manaCost'];
  mainDatasource = [];
  sideDatasource = [];
  expandedElement: DeckCard | null;
  loadingContent: boolean;

  constructor(private activatedRoute: ActivatedRoute, private deckService: DeckService) {
    const id = this.activatedRoute.snapshot.params.id;
    this.loadingContent = true;
    this.deckService.getDeck(id).subscribe(deck => {
      console.log(deck);
      this.deck = deck;
      this.mainDatasource = this.deck.main;
      this.sideDatasource = this.deck.sideboard;
      this.loadingContent = false;
    }, error => {
      this.loadingContent = false;
      console.log(error);
    });
  }

  ngOnInit(): void {
  }

}
