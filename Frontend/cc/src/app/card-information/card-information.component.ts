import {Component, OnInit} from '@angular/core';
import {ActivatedRoute} from '@angular/router';
import {CardService} from '../services/card.service';
import {Location} from '@angular/common';
import {UtilsService} from '../services/utils.service';

@Component({
  selector: 'cc-card-information',
  templateUrl: './card-information.component.html',
  styleUrls: ['./card-information.component.css']
})
export class CardInformationComponent implements OnInit {

  card: any;
  languages = [];
  selectedLang: string;
  selectedSet: any;

  legalities = {
    standard: 'Not Legal',
    historic: 'Not Legal',
    pioneer: 'Not Legal',
    modern: 'Not Legal',
    commander: 'Not Legal',
    vintage: 'Not Legal',
    legacy: 'Not Legal',
    penny: 'Not Legal',
    duel: 'Not Legal',
  };

  constructor(private activatedRoute: ActivatedRoute, private cardService: CardService, private location: Location, public utilsService: UtilsService) {
    const id = this.activatedRoute.snapshot.params.id;
    this.cardService.getCardInfo(id).subscribe(card => {
      this.card = card;
      this.setupCard();
      this.initializeLanguages();
      this.initializeLegalities();
      console.log(this.card);
    }, error => console.error(error));
  }

  ngOnInit(): void {
  }

  back(): void {
    this.location.back();
  }

  setupCard(): void {
    for (const tr of this.card.card.cardTranslation) {
      if (tr === this.card.id) {
        this.card.card.cardTranslation.splice(this.card.card.cardTranslation.indexOf(tr), 1);
        this.card.card.cardTranslation.push({
          id: this.card.id,
          name: this.card.name,
          description: this.card.description,
          lore: this.card.lore,
          lang: this.card.lang,
          card: this.card.card.id,
          cardSet: this.card.cardSet
        });
        break;
      }
    }
    this.selectedSet = this.card.cardSet[0];
  }

  initializeLanguages(): void {
    this.languages.push(this.card.lang);
    this.selectedLang = this.card.lang;
    for (const t of this.card.card.cardTranslation) {
      if (t.lang !== this.selectedLang) {
        this.languages.push(t.lang);
      }
    }
  }

  initializeLegalities(): void {
    for (const l of this.card.card.legalities) {
      if (l.format === 'Standard') {
        this.legalities.standard = l.legality;
      }
      if (l.format === 'Historic') {
        this.legalities.historic = l.legality;
      }
      if (l.format === 'Pioneer') {
        this.legalities.standard = l.legality;
      }
      if (l.format === 'pioneer') {
        this.legalities.standard = l.legality;
      }
      if (l.format === 'Modern') {
        this.legalities.modern = l.legality;
      }
      if (l.format === 'Commander') {
        this.legalities.commander = l.legality;
      }
      if (l.format === 'Legacy') {
        this.legalities.legacy = l.legality;
      }
      if (l.format === 'Vintage') {
        this.legalities.vintage = l.legality;
      }
      if (l.format === 'Duel') {
        this.legalities.duel = l.legality;
      }
      if (l.format === 'Penny') {
        this.legalities.penny = l.legality;
      }
    }
  }

  changeLanguage(lang: string): void {
    for (const tr of this.card.card.cardTranslation) {
      if (tr.lang === lang) {
        this.card.id = tr.id;
        this.card.name = tr.name;
        this.card.description = tr.description;
        this.card.lore = tr.lore;
        this.card.lang = tr.lang;
        this.card.cardSet = tr.cardSet;
        this.selectedLang = lang;
        break;
      }
    }
    this.selectedSet = this.card.cardSet[0];
  }

  changeSet(set: string, cNumber: number): void {
    for (const s of this.card.cardSet) {
      if (set === s.cardSet && cNumber === s.cardNumber) {
        this.selectedSet = s;
        break;
      }
    }
  }
}
