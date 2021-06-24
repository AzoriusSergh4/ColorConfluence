import {Component, OnInit} from '@angular/core';
import {DeckService} from '../services/deck.service';
import {Deck} from '../deck/deck.component';
import {Format} from '../deck-creation/deck-creation.component';
import {HttpParams} from '@angular/common/http';
import {UtilsService} from '../services/utils.service';

export interface ColorCheckBox {
  color: string;
  abbreviation: string;
  checked: boolean;
}

@Component({
  selector: 'cc-deck-list',
  templateUrl: './deck-list.component.html',
  styleUrls: ['./deck-list.component.css']
})
export class DeckListComponent implements OnInit {

  decks: Deck[];
  formats: Format[];
  displayedColumns: string[] = ['name', 'colors', 'format', 'user'];
  colors: ColorCheckBox[] = [
    {color: 'White', abbreviation: 'W', checked: false},
    {color: 'Blue', abbreviation: 'U', checked: false},
    {color: 'Black', abbreviation: 'B', checked: false},
    {color: 'Red', abbreviation: 'R', checked: false},
    {color: 'Green', abbreviation: 'G', checked: false},
    {color: 'Colorless', abbreviation: 'C', checked: false}
  ];
  deckName: string;
  format: string;
  currentParams: HttpParams;

  pageSize = 10;
  totalCardsFound: number;

  constructor(private deckService: DeckService, public utilsService: UtilsService) {
    this.currentParams = new HttpParams();
    this.currentParams = this.currentParams.append('page', '0');
    this.deckService.getAllDecksWithCriteria(this.currentParams).subscribe(response => {
      this.decks = response.content;
      this.totalCardsFound = response.totalElements;
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
    this.currentParams = this.currentParams.set('page', (event.page - 1).toString());
    this.searchDecks();
  }

  searchDecks() {
    let params = new HttpParams();
    params = params.append('page', '0');
    if (!this.isFieldEmpty(this.deckName)) {
      params = params.append('name', this.deckName);
    }
    if (!this.isFieldEmpty(this.format)) {
      params = params.append('format', this.format);
    }
    if (this.colors.some(color => color.checked === true)) {
      let colors = '';
      this.colors.filter(color => color.checked === true).forEach(color => colors += color.abbreviation);
      params = params.append('colors', colors);
    }
    this.currentParams = params;
    this.deckService.getAllDecksWithCriteria(params).subscribe(response => {
      this.decks = response.content;
      this.totalCardsFound = response.totalElements;
      console.log(this.decks);
    });
  }

  isFieldEmpty(field: string) {
    return field === null || field === undefined || field === '';
  }

}
