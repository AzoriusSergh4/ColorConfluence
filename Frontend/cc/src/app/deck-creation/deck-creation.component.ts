import {Component, Inject, OnInit} from '@angular/core';
import {CdkDragDrop, copyArrayItem, moveItemInArray, transferArrayItem} from '@angular/cdk/drag-drop';
import {CardService} from '../services/card.service';
import {DeckService} from '../services/deck.service';
import {Sort} from '@angular/material/sort';
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from '@angular/material/dialog';
import {BaseComponent} from '../base/base.component';
import {ActivatedRoute, Router} from '@angular/router';
import {MatSnackBar} from '@angular/material/snack-bar';
import {IPageChangeEvent} from '@covalent/core/paging';
import {LoginService} from '../services/login.service';

export interface DeckCardForm {
  id: number;
  quantity: number;
  card?: any;
}

export interface Format {
  id?: number;
  name: string;
}

export interface DeckForm {
  id?: number;
  name: string;
  comments: string;
  draft: boolean;
  format: Format;
  commanders: DeckCardForm[];
  main: DeckCardForm[];
  sideboard: DeckCardForm[];
  folderId: number;
}

@Component({
  selector: 'cc-deck-creation',
  templateUrl: './deck-creation.component.html',
  styleUrls: ['./deck-creation.component.css']
})
export class DeckCreationComponent extends BaseComponent implements OnInit {


  deckForm: DeckForm;
  formats: Format[];

  cardsToAdd: number;

  currentSort: Sort;

  pageSize = 10;
  totalCardsFound: number;
  currentName: string;
  cardsFound: any[];

  commanders: any[];
  deck: any[];
  sideboard: any[];

  constructor(protected router: Router, private route: ActivatedRoute, private cardService: CardService, private deckService: DeckService, public dialog: MatDialog, private snackBar: MatSnackBar, public loginService: LoginService) {
    super(router);
    if (!loginService.isLogged) {
      this.redirectToHome();
    }
    this.deckService.getAllFormats().subscribe(formats => {
      this.formats = formats;
      console.log(formats);
    }, error => {
      console.log(error);
      this.formats = [];
    });
    this.deckForm = {
      name: '',
      comments: '',
      format: null,
      draft: false,
      main: [],
      commanders: [],
      sideboard: [],
      folderId: 0
    };
    this.route.queryParams.subscribe(params => {
      if (params.id) {
        this.deckService.getDeck(+params.id).subscribe(response => {
          if (response.user.id === loginService.user.id) {
            this.deckForm.id = response.id;
            this.deckForm.name = response.name;
            this.deckForm.comments = response.comments;
            this.deckForm.draft = response.draft;
            this.deckForm.format = response.format;
            this.deckForm.main = response.main;
            this.deckForm.commanders = response.commander;
            this.deckForm.sideboard = response.sideboard;
            console.log(this.deckForm);
            this.deckForm.main.forEach(c => {
              const card = c.card.cardTranslation[0];
              card.quantity = c.quantity;
              card.card = c.card;
              this.deck.push(card);
            });
            this.deckForm.commanders.forEach(c => {
              const card = c.card.cardTranslation[0];
              card.quantity = c.quantity;
              card.card = c.card;
              this.commanders.push(card);
            });
            this.deckForm.sideboard.forEach(c => {
              const card = c.card.cardTranslation[0];
              card.quantity = c.quantity;
              card.card = c.card;
              this.sideboard.push(card);
            });
          } else {
            this.redirectToHome();
          }
        });
      }else if (params.folderId) {
        this.deckForm.folderId = +params.folderId;
        console.log(this.deckForm);
      }
    });
    this.cardsFound = [];
    this.commanders = [];
    this.deck = [];
    this.sideboard = [];
    this.cardsToAdd = 4;
  }

  ngOnInit(): void {
  }

  compareFormats(f1: Format, f2: Format) {
    return f1 && f2 && f1.id === f2.id;
  }

  checkDeck(): any {
    const checkCardForCopies = (card: any) => {
      const type = card.card.cardType as string;
      const description = card.description as string;
      return (type.includes('Basic') || (description.includes('A deck can have any number of cards named')));
    };

    const checkCards = (deck: any[], form: DeckCardForm[], maximumCopies: number) => {
      deck.forEach(c => {
        form.push(
          {
            id: c.card.id,
            quantity: c.quantity
          });
        if (this.checkLegality(c)) {
          errors.illegalCards.push(c.name);
        }
        if (c.quantity > maximumCopies && !checkCardForCopies(c)) {
          errors.wrongCopies.push(c.name);
        }
      });
    };

    this.deckForm.main = [];
    this.deckForm.commanders = [];
    this.deckForm.sideboard = [];
    let errors: {
      existError: boolean
      wrongSize: boolean,
      noCommander: boolean,
      illegalCards: string[],
      wrongCopies: string []
    };
    errors = {
      existError: false,
      wrongSize: false,
      noCommander: false,
      illegalCards: [],
      wrongCopies: []
    };
    let info: {
      defaultSize: number;
    };
    info = {
      defaultSize: 0
    };

    // Check legality and  max copies

    if (this.deckForm.format && this.deckForm.format.name === 'Commander') {
      info.defaultSize = 1;
      if (this.commanders.length === 0){
        errors.noCommander = true;
      }
      if (this.sumCards(this.commanders) + this.sumCards(this.deck) !== 100){
        errors.wrongSize = true;
      }
      checkCards(this.commanders, this.deckForm.commanders, info.defaultSize);
    } else {
      info.defaultSize = 4;
      if (this.sumCards(this.deck) < 60 || this.sumCards(this.sideboard) > 15){
        errors.wrongSize = true;
      }
      checkCards(this.sideboard, this.deckForm.sideboard, info.defaultSize);
    }
    checkCards(this.deck, this.deckForm.main, info.defaultSize);

    errors.existError = errors.noCommander || errors.wrongSize || errors.wrongCopies.length > 0 || errors.illegalCards.length > 0;
    return {
      error: errors,
      information: info
    };
  }

  openSaveDialog() {
    const info = this.checkDeck();
    const dialogRef = this.dialog.open(SaveDeckDialog, {
      width: '50%',
      data: info
    });

    dialogRef.afterClosed().subscribe(result => {
      if (result){
        if (info.error.existError) {
          this.deckForm.draft = true;
        }
        this.saveDeck();
      }
    });
  }

  saveDeck() {
    console.log(this.deckForm);
    this.deckService.saveDeck(this.deckForm).subscribe(id => {
      this.snackBar.open('The deck has been saved successfully', 'OK', {duration: 3000});
      this.router.navigate(['/deck/' + id]);
    }, error => {
      console.log(error);
      this.snackBar.open('There has been an error saving the deck. Please try again later', 'OK', {duration: 3000});
    });
  }

  drop(event: CdkDragDrop<any[]>) {
    console.log(event.previousContainer);
    if (event.previousContainer === event.container) {
      moveItemInArray(event.container.data, event.previousIndex, event.currentIndex);
    } else {
      const i = event.container.data.indexOf(event.previousContainer.data[event.previousIndex]);
      if (i === -1){
        if (event.previousContainer.id === 'cardsFoundList'){
          copyArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
        } else {
          transferArrayItem(event.previousContainer.data, event.container.data, event.previousIndex, event.currentIndex);
        }
        event.container.data[event.currentIndex].quantity = this.cardsToAdd;
      } else {
        event.container.data[i].quantity += this.cardsToAdd;
      }
    }
    console.log(event.container);
    this.sortData(this.currentSort);
  }

  pageChange(event: IPageChangeEvent) {
    if (this.currentName.length > 2) {
      this.cardService.getFullCardsByName(this.currentName, event.page - 1, this.pageSize).subscribe(response => {
        this.cardsFound = response.content;
        this.totalCardsFound = response.totalElements;
      });
    }
  }

  autofillCard(card: string) {
    this.currentName = card;
  }

  checkQuantity(event: Event) {
    const el = event.target as HTMLInputElement;
    // @ts-ignore
    if (el.value > 99) {
      el.value = '99';
    }
    // @ts-ignore
    if (el.value === '' || el.value < 1) {
      el.value = '1';
    }
  }

  sumCards(list: any[]): number {
    let total = 0;
    list.forEach(card => {
      total += card.quantity;
    });
    return total;
  }

  removeCard(list: any[], index: number) {
    list.splice(index, 1);
  }

  checkLegality(card: any): boolean {
    let legalities: any[];
    legalities = card.card.legalities;
    if (this.deckForm.format) {
      let illegal = true;
      legalities.forEach(l => {
        if (l.format === this.deckForm.format.name && l.legality === 'Legal') {
          illegal = false;
        }
      });
      return illegal;
    } else {
      return false;
    }
  }

  sortData(sort: Sort) {

    this.currentSort = sort;
    this.deck = sortList(sort, this.deck);
    this.commanders = sortList(sort, this.commanders);
    this.sideboard = sortList(sort, this.sideboard);

    function sortList(sortEvent: Sort, list: any[]): any[]{
      if (sort === undefined || !sort.active || sort.direction === '') {
        return list;
      }
      const data = list.slice();
      list = data.sort((a, b) => {
        const isAsc = sort.direction === 'asc';
        switch (sort.active) {
          case 'name': return compare(a.card.name, b.card.name, isAsc);
          case 'type': return compare(a.card.cardType, b.card.cardType, isAsc);
          case 'manaCost': return compare(a.card.manaCost, b.card.manaCost, isAsc);
          default: return 0;
        }
      });

      return list;
    }
    function compare(a: number | string, b: number | string, isAsc: boolean) {
      return (a < b ? -1 : 1) * (isAsc ? 1 : -1);
    }
  }
}

@Component({
  selector: 'save-deck-dialog',
  templateUrl: 'save-deck-dialog.html',
})
// tslint:disable-next-line:component-class-suffix
export class SaveDeckDialog {

  constructor(
    public dialogRef: MatDialogRef<SaveDeckDialog>,
    @Inject(MAT_DIALOG_DATA) public data: any) {}

}
