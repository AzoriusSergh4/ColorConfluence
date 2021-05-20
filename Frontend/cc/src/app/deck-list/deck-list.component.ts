import { Component, OnInit } from '@angular/core';
import {DeckService} from '../services/deck.service';
import {Deck} from '../deck/deck.component';
import {Format} from '../deck-creation/deck-creation.component';

@Component({
  selector: 'cc-deck-list',
  templateUrl: './deck-list.component.html',
  styleUrls: ['./deck-list.component.css']
})
export class DeckListComponent implements OnInit {

  decks: Deck[];
  formats: Format[];
  displayedColumns: string[] = ['name', 'colors', 'format', 'user'];

  pageSize = 10;
  totalCardsFound: number;

  constructor(private deckService: DeckService) {
    this.deckService.getAllDecks(0).subscribe(response => {
      this.decks = response.content;
      console.log(this.decks);
    });
    this.deckService.getAllFormats().subscribe(formats => {
      this.formats = formats;
    }, error => {
      console.log(error);
      this.formats = [];
    });
  }

  ngOnInit(): void {
  }

  pageChange(event: any) {

  }

}
