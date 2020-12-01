import {Component} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CardService} from '../services/card.service';

@Component({
  selector: 'cc-card-search',
  templateUrl: './card-search.component.html',
  styleUrls: ['./card-search.component.css']
})
export class CardSearchComponent {
  cards: any;
  name: string;

  constructor(private route: ActivatedRoute, private cardService: CardService) {
    let name;
    this.route.queryParams.subscribe(params => {
      name = params['name'];
      this.name = name;
      this.cardService.getCardsByName(name).subscribe(cards => this.cards = cards, error => console.error(error));
    });
  }
}
