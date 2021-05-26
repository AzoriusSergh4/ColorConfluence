import { Injectable } from '@angular/core';
import {CommonService} from './common.service';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {DeckForm} from '../deck-creation/deck-creation.component';

const FORMAT_URL = '/api/format';
const DECKS_URL = '/decks';
const DECK_URL = '/api/deck';
const CREATE_URL = '/create';
const ALL_URL = '/all';

@Injectable({
  providedIn: 'root'
})
export class DeckService extends CommonService{

  constructor(http: HttpClient) {
    super(http);
  }

  /**
   * Get the deck information
   * @param id the deck id
   */
  getDeck(id: number) {
    return this.apiGetRequest(DECK_URL + '/' + id);
  }

  /**
   * Get all pages based on the page
   * @param page the page index
   */
  getAllDecks(page: number) {
    return this.apiGetRequest(DECK_URL + DECKS_URL + '?page=' + page);
  }

  /**
   * Get a page of decks with the specified criteria
   * @param criteria the criteria to filter
   */
  getAllDecksWithCriteria(criteria: HttpParams) {
    return this.apiGetRequestWithCriteria(DECK_URL + DECKS_URL, criteria);
  }

  /**
   * Save the deck
   * @param deckForm the deck data
   */
  saveDeck(deckForm: DeckForm){
    const body = JSON.stringify(deckForm);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.apiPostRequest(DECK_URL + CREATE_URL, body, headers);
  }

  /**
   * Get all formats
   */
  getAllFormats() {
    return this.apiGetRequest(FORMAT_URL + ALL_URL);
  }
}
