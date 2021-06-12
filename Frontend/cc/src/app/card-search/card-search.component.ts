import {Component, OnInit} from '@angular/core';
import {ActivatedRoute, Router} from '@angular/router';
import {CardService} from '../services/card.service';
import {AbstractControl, FormBuilder, FormControl, FormGroup, ValidatorFn, Validators} from '@angular/forms';
import {MatChipInputEvent} from '@angular/material/chips';
import {COMMA, ENTER} from '@angular/cdk/keycodes';
import {HttpParams} from '@angular/common/http';
import {PageEvent} from '@angular/material/paginator';
import {UtilsService} from '../services/utils.service';

interface Format {
  name: string;
  publicName: string;
}

@Component({
  selector: 'cc-card-search',
  templateUrl: './card-search.component.html',
  styleUrls: ['./card-search.component.css']
})
export class CardSearchComponent implements OnInit{

  constructor(private router: Router, private route: ActivatedRoute, private cardService: CardService, private fb: FormBuilder, public utilsService: UtilsService) {
    this.pageIndex = 0;
    this.currentParams = new HttpParams();
  }

  readonly separatorKeysCodes: number[] = [ENTER, COMMA];
  readonly colorRegex = '(^$|^(?:(<|=|<=))(?:([WURGBC])(?!.*\\2))+$)';
  readonly statRegex = '(^$|^(?:(<|=|<=|>|>=|!=))(?:([0-9])(?!.*\\2))+$)';

  pageIndex: number;
  sets: any;
  cards: any;
  currentTypes: string[] = [];
  currentParams: HttpParams;
  formats: Format[] = [
    {name: 'standard', publicName: 'Standard'},
    {name: 'duel', publicName: 'Duel'},
    {name: 'legacy', publicName: 'Legacy'},
    {name: 'modern', publicName: 'Modern'},
    {name: 'vintage', publicName: 'Vintage'},
    {name: 'commander', publicName: 'Commander'},
    {name: 'historic', publicName: 'Historic'},
    {name: 'pioneer', publicName: 'Pioneer'},
    {name: 'penny', publicName: 'Penny'}
  ];

  // Colors
  colorsControl = '';
  colorCriteria = '';

  // Stats
  cmcControl = '';
  cmcCriteria = '';
  powerControl = '';
  powerCriteria = '';
  toughnessControl = '';
  toughnessCriteria = '';

  cardFilterForm: FormGroup;

  ngOnInit() {
    this.cardFilterForm = this.fb.group({
    name: [''],
    text: [''],
    lore: [''],
    type: [[]],
    colors: ['', Validators.pattern(this.colorRegex)],
    manaCost: [''],
    cmc: ['', Validators.pattern(this.statRegex)],
    power: ['', Validators.pattern(this.statRegex)],
    toughness: ['', Validators.pattern(this.statRegex)],
    legality: this.fb.group({
      format: [''],
      legality: ['']
    }),
    set: [''],
    lang: [''],
    rarity: ['']
  });

    this.cardFilterForm.get('legality').setValidators(this.LegalityValidator());
    let name;
    this.route.queryParams.subscribe(params => {
      name = params.name;
      if (name){
        this.currentParams = this.currentParams.append('name', name);
        this.cardFilterForm.setControl('name', new FormControl(name));
        this.cardService.getCardsByName(name).subscribe(cards => {
          this.cards = cards;
        }, error => console.error(error));
      }
    });
    this.cardService.getAllSets().subscribe(sets => this.sets = sets, error => console.error(error));

  }

  onSubmit() {
    console.log(this.cardFilterForm.valid);
    if (this.cardFilterForm.valid){
      let params = new HttpParams();
      if (!this.isFieldEmpty(this.cardFilterForm.get('name'))) { params = params.append('name', this.cardFilterForm.get('name').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('text'))) { params = params.append('text', this.cardFilterForm.get('text').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('lore'))) { params = params.append('lore', this.cardFilterForm.get('lore').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('type'))) { params = params.append('type', this.cardFilterForm.get('type').value.toString()); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('colors'))) {  params = params.append('colors', this.cardFilterForm.get('colors').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('manaCost'))) {  params = params.append('manaCost', this.cardFilterForm.get('manaCost').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('cmc'))) {  params = params.append('cmc', this.cardFilterForm.get('cmc').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('power'))) {   params = params.append('power', this.cardFilterForm.get('power').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('toughness'))) {   params = params.append('toughness', this.cardFilterForm.get('toughness').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('set'))) {  params = params.append('set', this.cardFilterForm.get('set').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('lang'))) {  params = params.append('lang', this.cardFilterForm.get('lang').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('rarity'))) {   params = params.append('rarity', this.cardFilterForm.get('rarity').value); }
      if (!this.isFieldEmpty(this.cardFilterForm.get('legality').get('legality'))) { params = params.append(this.cardFilterForm.get('legality').get('format').value, this.cardFilterForm.get('legality').get('legality').value); }
      params = params.append('page', '0');
      this.currentParams = params;
      this.cardService.getCardsByCriteria(params).subscribe(cards => this.cards = cards, error => console.error(error));
    }
  }

  addType(event: MatChipInputEvent): void {
    const input = event.input;
    const value = event.value;

    if ((value || '').trim()) {
      this.currentTypes.push(value.trim());
      this.cardFilterForm.setControl('type', new FormControl(this.currentTypes));
    }

    // Reset the input value
    if (input) {
      input.value = '';
    }
  }

  removeType(type: string): void {
    const index = this.currentTypes.indexOf(type);

    if (index >= 0) {
      this.currentTypes.splice(index, 1);
    }
  }

  colorChange(event){
    if (event.checked){
      this.colorsControl += event.source.value;
    } else {
      this.colorsControl = this.colorsControl.replace(event.source.value, '');
    }
    this.checkColorsValidation();
  }

  colorCriteriaChange(event){
    if (event.value === undefined){
      this.colorCriteria = '';
    }
    this.checkColorsValidation();
  }

  checkColorsValidation(){
    this.cardFilterForm.setControl('colors', new FormControl(this.colorCriteria + this.colorsControl, Validators.pattern(this.colorRegex)));
  }

  statCriteriaChange(event, stat){
    if (event.value === undefined){
      switch (stat){
        case 'cmc':
          this.cmcCriteria = '';
          break;
        case 'power':
          this.powerCriteria = '';
          break;
        case 'toughness':
          this.toughnessCriteria = '';
          break;
      }
    }
    this.checkStatValidation(stat);
  }

  statChange(event, stat){
    if (event.data === null){
      switch (stat){
        case 'cmc':
          this.cmcControl = '';
          break;
        case 'power':
          this.powerControl = '';
          break;
        case 'toughness':
          this.toughnessControl = '';
          break;
      }
    }
    this.checkStatValidation(stat);
  }

  checkStatValidation(stat) {
    switch (stat) {
      case 'cmc':
        this.cardFilterForm.setControl('cmc', new FormControl(this.cmcCriteria + this.cmcControl, Validators.pattern(this.statRegex)));
        break;
      case 'power':
        this.cardFilterForm.setControl('power', new FormControl(this.powerCriteria + this.powerControl, Validators.pattern(this.statRegex)));
        break;
      case 'toughness':
        this.cardFilterForm.setControl('toughness', new FormControl(this.toughnessCriteria + this.toughnessControl, Validators.pattern(this.statRegex)));
        break;
    }
  }

  isFieldEmpty(field: AbstractControl){
    return field.value === null || field.value === undefined || field.value === '' || field.value.length === 0;
  }

  public LegalityValidator(): ValidatorFn{

    return (group: FormGroup): {[key: string]: boolean} => {
      const invalid = (group.controls.legality.value === undefined || group.controls.legality.value === '') ? (group.controls.format.value !== undefined && group.controls.format.value !== '') : (group.controls.format.value === undefined || group.controls.format.value === '');
      return invalid ? {
        notAllFilled: true
      } : null ;
    };
  }

  handlePageEvent(event: PageEvent) {
    this.currentParams = this.currentParams.set('page', event.pageIndex.toString());
    console.log(this.currentParams);
    this.cardService.getCardsByCriteria(this.currentParams).subscribe(cards => this.cards = cards, error => console.error(error));
  }
}
