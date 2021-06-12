import {Component, Inject, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {DeckService} from '../services/deck.service';
import {Format} from '../deck-creation/deck-creation.component';
import {LoginService, User} from '../services/login.service';
import {animate, state, style, transition, trigger} from '@angular/animations';
import * as _ from 'lodash';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {DeleteEntityDialog} from '../deck-user/deck-user.component';
import {BaseComponent} from '../base/base.component';
import {MatSnackBar} from '@angular/material/snack-bar';
import {UtilsService} from '../services/utils.service';

export interface DeckCard {
  id: number;
  card: any;
  quantity: number;
}

export interface Deck {
  id: number;
  name: string;
  colors: string;
  commander: DeckCard[];
  main: DeckCard[];
  sideboard: DeckCard[];
  comments: string;
  format: Format;
  user: User;
}

export interface Probability {
  quantity: number;
  name: string;
  opening: string;
  turn1: string;
  turn2: string;
  turn3: string;
  turn4: string;
  turn5: string;
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
export class DeckComponent extends BaseComponent implements OnInit {

  deck: Deck;

  displayedColumnsDeck: string[] = ['quantity', 'name', 'cardType', 'manaCost'];
  displayedColumnsProb: string[] = ['quantity', 'name', 'opening', 'turn1', 'turn2', 'turn3', 'turn4', 'turn5'];
  mainDatasource = [];
  sideDatasource = [];
  expandedElement: DeckCard | null;
  loadingContent: boolean;

  manaD: any[];
  config: any = {
    cmcD: {
      xAxis: [
        {
          data: ['CC0', 'CC1', 'CC2', 'CC3', 'CC4', 'CC5', 'CC6', 'CC7+'],
        },
        {
          show: true,
          type: 'value',
          boundaryGap: false,
        },
      ],
      yAxis: [
        {
          show: true,
          type: 'value',
          axisLabel: { inside: false },
          max(value) {
            return Math.ceil(value.max / 10) * 10;
          }
        },
      ],
      series: [
        {
          type: 'bar',
          itemStyle: {
            opacity: 0.95,
            color: '#00838f',
          },
          markPoint: {
            data: [{ name: 'test', value: 130, xAxis: 1, yAxis: 130 }],
          },
          name: 'CMC',
          data: [],
        }
      ],
      tooltip: {
        show: true,
        trigger: 'item',
        showContent: true,
      },
    },
    typeD: {
      series: [
        {
          type: 'pie',
          itemStyle: {
            opacity: 0.95,
          },
          name: 'Type',
          radius: [0, '75%'],
          data: [
          ],
        },
      ],

      tooltip: {
        show: true,
        trigger: 'item',
        showContent: true,
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
    },
    manaD: {
      series: [
        {
          type: 'pie',
          itemStyle: {
            opacity: 0.95,
          },
          name: 'Color',
          radius: [0, '75%'],
          data: [
          ],
        },
      ],

      tooltip: {
        show: true,
        trigger: 'item',
        showContent: true,
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
    },
    manaSourceD: {
      series: [
        {
          type: 'pie',
          itemStyle: {
            opacity: 0.95,
          },
          name: 'Color',
          radius: [0, '75%'],
          data: [
          ],
        },
      ],

      tooltip: {
        show: true,
        trigger: 'item',
        showContent: true,
        formatter: '{a} <br/>{b} : {c} ({d}%)',
      },
    }
  };
  probabilities: Probability[];

  openingHand: any[];

  exportList: string;
  exportListLines: number;

  constructor(protected router: Router, private activatedRoute: ActivatedRoute, private deckService: DeckService, public dialog: MatDialog, private snackBar: MatSnackBar, public loginService: LoginService, public utilsService: UtilsService) {
    super(router);
    const id = this.activatedRoute.snapshot.params.id;
    this.loadingContent = true;
    this.deckService.getDeck(id).subscribe(deck => {
      console.log(deck);
      this.deck = deck;
      this.mainDatasource = this.deck.main;
      this.sideDatasource = this.deck.sideboard;
      this.loadingContent = false;
      this.initializeCharts();
      this.initializeProbabilities();
      this.mulligan();
      this.prepareExportList();
    }, error => {
      this.loadingContent = false;
      console.log(error);
    });
  }

  ngOnInit(): void {
  }

  mulligan() {
    this.openingHand = _.sampleSize(this.deck.main, 7);
  }

  initializeCharts() {
    this.initializeCmcDistribution();
    this.initializeTypeDistribution();
    this.initializeManaDistribution();
    this.initializeManaSourceDistribution();
  }

  initializeProbabilities() {
    this.probabilities = [];
    this.deck.main.forEach(c => {
      this.probabilities.push({
        quantity: c.quantity,
        name: c.card.name,
        opening: ((1 - this.hypergeometrical(this.sumCards(this.deck.main), c.quantity, 7, 0)) * 100).toFixed(2) + '%',
        turn1: ((1 - this.hypergeometrical(this.sumCards(this.deck.main), c.quantity, 8, 0)) * 100).toFixed(2) + '%',
        turn2: ((1 - this.hypergeometrical(this.sumCards(this.deck.main), c.quantity, 9, 0)) * 100).toFixed(2) + '%',
        turn3: ((1 - this.hypergeometrical(this.sumCards(this.deck.main), c.quantity, 10, 0)) * 100).toFixed(2) + '%',
        turn4: ((1 - this.hypergeometrical(this.sumCards(this.deck.main), c.quantity, 11, 0)) * 100).toFixed(2) + '%',
        turn5: ((1 - this.hypergeometrical(this.sumCards(this.deck.main), c.quantity, 12, 0)) * 100).toFixed(2) + '%'
      });
    });
  }

  sumCards(list: any[]): number {
    let total = 0;
    list.forEach(card => {
      total += card.quantity;
    });
    return total;
  }

  initializeCmcDistribution() {
    let data: number[];
    data = [0, 0, 0, 0, 0, 0, 0, 0];
    const spells = this.deck.main.filter(c => !(c.card.cardType as string).match('Land'));
    spells.forEach(c => {
      const cmc = c.card.cmc as number;
      if (cmc < 7) {
        data[cmc] += c.quantity;
      } else {
        data[7] += c.quantity;
      }
    });
    this.config.cmcD.series[0].data = data;
  }

  initializeTypeDistribution() {
    let data: any[];
    data = [
      { name: 'Creature', value: 0, itemStyle: { color: '#E87F52' }},
      { name: 'Instant', value: 0, itemStyle: { color: '#6AD0CA' }},
      { name: 'Sorcery', value: 0, itemStyle: { color: '#B079AB' }},
      { name: 'Enchantment', value: 0, itemStyle: { color: '#DFCA45' }},
      { name: 'Artifact', value: 0, itemStyle: { color: '#DADADA' }},
      { name: 'Planeswalker', value: 0, itemStyle: { color: '#DE888D' }},
      { name: 'Land', value: 0, itemStyle: { color: '#6BD59A' }}
    ];
    this.deck.commander.forEach(c => {
      this.updateTypeData(c, data);
    });
    this.deck.main.forEach(c => {
      this.updateTypeData(c, data);
    });
    data = data.filter(d => d.value > 0);
    this.config.typeD.series[0].data = data;
  }

  updateTypeData(c: DeckCard, data: any[]) {
    if ((c.card.cardType as string).match('Creature')) {
      data[0].value += c.quantity;
    } else if ((c.card.cardType as string).match('Instant')) {
      data[1].value += c.quantity;
    } else if ((c.card.cardType as string).match('Sorcery')) {
      data[2].value += c.quantity;
    } else if ((c.card.cardType as string).match('Enchantment')) {
      data[3].value += c.quantity;
    } else if ((c.card.cardType as string).match('Artifact')) {
      data[4].value += c.quantity;
    } else if ((c.card.cardType as string).match('Planeswalker')) {
      data[5].value += c.quantity;
    } else if ((c.card.cardType as string).match('Land')) {
      data[6].value += c.quantity;
    }
  }

  initializeManaDistribution() {
    let data: any[];
    data = [
      { name: 'White', value: 0, itemStyle: { color: '#FFFAA2' }},
      { name: 'Blue', value: 0, itemStyle: { color: '#86B4E3' }},
      { name: 'Black', value: 0, itemStyle: { color: '#1D056C' }},
      { name: 'Red', value: 0, itemStyle: { color: '#FF5C5C' }},
      { name: 'Green', value: 0, itemStyle: { color: '#63C97E' }},
      { name: 'Colorless', value: 0, itemStyle: { color: '#DADADA' }}
    ];
    this.deck.commander.forEach(c => {
      this.updateManaData(c, data);
    });
    this.deck.main.forEach(c => {
      this.updateManaData(c, data);
    });
    data = data.filter(d => d.value > 0);
    this.config.manaD.series[0].data = data;
  }

  updateManaData(c: DeckCard, data: any[]) {
    const white = this.countManaSymbols(c.card.manaCost, 'W');
    const blue = this.countManaSymbols(c.card.manaCost, 'U');
    const black = this.countManaSymbols(c.card.manaCost, 'B');
    const red = this.countManaSymbols(c.card.manaCost, 'R');
    const green = this.countManaSymbols(c.card.manaCost, 'G');
    const colorless = this.countManaSymbols(c.card.manaCost, '[C1-9]');
    data[0].value += white * c.quantity;
    data[1].value += blue * c.quantity;
    data[2].value += black * c.quantity;
    data[3].value += red * c.quantity;
    data[4].value += green * c.quantity;
    data[5].value += colorless * c.quantity;
  }

  countManaSymbols(manaCost: string, regex: string){
    if (manaCost === null) { return 0; }
    const match = manaCost.match(new RegExp(regex, 'g')) || [];
    let i: number;
    i = 0;
    match.forEach(m => {
      if (m.match(new RegExp('[1-9]'))) {
        i += +m;
        match.splice(match.indexOf(m), 1);
      }
    });
    return i + match.length;
  }

  initializeManaSourceDistribution() {
    let data: any[];
    data = [
      { name: 'White', value: 0, itemStyle: { color: '#FFFAA2' }},
      { name: 'Blue', value: 0, itemStyle: { color: '#86B4E3' }},
      { name: 'Black', value: 0, itemStyle: { color: '#1D056C' }},
      { name: 'Red', value: 0, itemStyle: { color: '#FF5C5C' }},
      { name: 'Green', value: 0, itemStyle: { color: '#63C97E' }},
      { name: 'Colorless', value: 0, itemStyle: { color: '#DADADA' }},
      { name: 'Any', value: 0, itemStyle: {color: '#7A604E'}}
    ];
    const lands = this.deck.main.filter(c => (c.card.cardType as string).match('Land'));
    lands.forEach(l => {
      this.updateManaSourceData(l, data);
    });

    data = data.filter(d => d.value > 0);
    this.config.manaSourceD.series[0].data = data;
  }

  updateManaSourceData(c: DeckCard, data: any[]) {
    // Basic Types
    if ((c.card.cardType as string).match('Plains') || (c.card.cardType as string).match('Island') || (c.card.cardType as string).match('Swamp') || (c.card.cardType as string).match('Mountain') || (c.card.cardType as string).match('Forest')) {
      if ((c.card.cardType as string).match('Plains')) {
        data[0].value += c.quantity;
      }
      if ((c.card.cardType as string).match('Island'))
      {
        data[1].value += c.quantity;
      }
      if ((c.card.cardType as string).match('Swamp'))
      {
        data[2].value += c.quantity;
      }
      if ((c.card.cardType as string).match('Mountain'))
      {
        data[3].value += c.quantity;
      }
      if ((c.card.cardType as string).match('Forest'))
      {
        data[4].value += c.quantity;
      }
    } else { // Deep search
      const text = c.card.cardTranslation[0].description as string;
      if (text) {
        // All colors
        if (text.match('Add one mana of any color')) {
          data[6].value += c.quantity;
        }
        // Specific colors
        let match = text.match(new RegExp('(Add (({[WGRBUC]})+, )*({[WGRBUC]})+ or ({[WGRBUC]})+)|(Add ({[WGRBUC]})+)', 'g'));
        if (match) {
          let symbols: string[];
          symbols = [];
          match.forEach( m => {
            const matchingSymbols = m.match(new RegExp('{[WGRBUC]}', 'g'));
            matchingSymbols.forEach(s => {
              if (symbols.indexOf(s) === -1) {
                symbols.push(s);
              }
            });
          });
          symbols.forEach(s => {
            switch (s){
              case '{W}': data[0].value += c.quantity; break;
              case '{U}': data[1].value += c.quantity; break;
              case '{B}': data[2].value += c.quantity; break;
              case '{R}': data[3].value += c.quantity; break;
              case '{G}': data[4].value += c.quantity; break;
              case '{C}': data[5].value += c.quantity; break;
            }
          });
        }
        // Numeric Colorless
        match = text.match(new RegExp('Add {[1-9]}', 'g'));
        if (match){
          match.forEach( m => {
            const matchingSymbols = m.match(new RegExp('[1-9]', 'g'));
            matchingSymbols.forEach(s => {
              data[5].value += +s * c.quantity;
            });
          });
        }
      }
    }
  }

  /* Mathematical functions */

  hypergeometrical(population: number, successes: number, sample: number, x: number): number {
    return (this.binomial(successes, x) * this.binomial(population - successes, sample - x)) / this.binomial(population, sample);
  }

  binomial(a: number, b: number): number {
    return this.factorial(a) / (this.factorial(b) * this.factorial(a - b));
  }

  factorial(n: number): number {
    let r = 1;
    for (let i = n; i > 0; i--){
      r *= i;
    }
    return r;
  }

  prepareExportList() {
    this.exportList = '';
    if (this.deck.commander.length > 0) {
      this.fillExportListSection('Commander', this.deck.commander);
      this.separateSections();
    }
    this.fillExportListSection('Deck', this.deck.main);
    if (this.deck.sideboard.length > 0) {
      this.separateSections();
      this.fillExportListSection('Sideboard', this.deck.sideboard);
    }
    this.exportListLines = this.exportList.match(/^/gm).length;
  }

  fillExportListSection(sectionName: string, section: any[]) {
    this.exportList += sectionName + '\n';
    section.forEach(c => {
      this.exportList += c.quantity + ' ' + c.card.name + '\n';
    });
  }

  separateSections() {
    this.exportList += '\n';
  }

  editDeck() {
    this.router.navigate(['/deck-editor'], {queryParams: {id: this.deck.id}});
  }

  openDeleteDeckDialog() {
    const dialogRef = this.dialog.open(DeleteEntityDialog, {
      width: '50%',
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result){
        this.deckService.deleteDeck(this.deck.id).subscribe(() => {
          this.goToMyDecks();
          this.snackBar.open('The deck was deleted successfully', 'OK', {duration: 3000});
        }, error => {
          this.snackBar.open('There was an error deleting the deck. Please try again later', 'OK', {duration: 3000});
          console.error(error);
        });
      }
    });
  }
}

@Component({
  selector: 'delete-deck-dialog',
  templateUrl: 'delete-deck-dialog.html',
})
// tslint:disable-next-line:component-class-suffix
export class DeleteDeckDialog {

  constructor(
    public dialogRef: MatDialogRef<DeleteDeckDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

}
