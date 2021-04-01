import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {CommonService} from './common.service';

const CARD_URL = '/api/card';
const CARD_INFO = CARD_URL + '/';
const FIND = CARD_URL + '/find';
const FIND_NAME = CARD_URL + '/find/name';
const COUNT_NAME = CARD_URL + '/count/name';
const SET_URL = '/api/set';
const SET_ALL = SET_URL + '/all';

@Injectable({
  providedIn: 'root'
})
export class CardService extends CommonService{

  constructor(http: HttpClient) {
    super(http);
  }

  /**
   * Get all cards that their names matches with the specified one
   * @param name the name to filter
   */
  getCardsByName(name: string){
    return this.apiGetRequest(FIND_NAME + '?name=' + encodeURIComponent(name));
  }

  /**
   * Get the number of cards that their names matches with the specified one
   * @param name the name to filter
   */
  countCardsByName(name: string) {
    return this.apiGetRequest(COUNT_NAME + '?name=' + encodeURIComponent(name));
  }

  /**
   * Get all existing sets
   */
  getAllSets(){
    return this.apiGetRequest(SET_ALL);
  }

  /**
   * Get all cards which attributes matches with the given criteria
   * @param criteria the card criteria to filter by
   * @param page page index
   */
  getCardsByCriteria(criteria: HttpParams){
    return this.apiGetRequestWithCriteria(FIND, criteria);
  }

  getCardInfo(id: number){
    return this.apiGetRequest(CARD_INFO + id);
  }
}

