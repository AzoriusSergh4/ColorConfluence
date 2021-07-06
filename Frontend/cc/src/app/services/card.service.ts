import {Injectable} from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import {CommonService} from './common.service';

const CARD_URL = '/api/cards';
const CARD_POPULARITY_URL = CARD_URL + '/popularity';
const CARD_INFO_URL = CARD_URL + '/';
const BASIC_URL = CARD_URL + '/basic';
const FULL_URL = CARD_URL + '/full';
const SIZE_URL = CARD_URL + '/size';
const SET_URL = '/api/sets';

@Injectable({
  providedIn: 'root'
})
export class CardService extends CommonService {

  constructor(http: HttpClient) {
    super(http);
  }

  /**
   * Get all card popularities
   * @param page the page
   */
  getCardPopularity(page: number) {
    return this.apiGetRequest(CARD_POPULARITY_URL + '?page=' + page);
  }

  /**
   * Get all cards that their names matches with the specified one
   * @param name the name to filter
   */
  getCardsByName(name: string) {
    let params = new HttpParams();
    params = params.append('name', name);
    params = params.append('page', '0');
    return this.apiGetRequestWithCriteria(BASIC_URL, params);
  }

  /**
   * Get all cards that their names matches with the specified one
   * @param name the name to filter
   * @param page the page
   * @param pageSize the size of the page
   */
  getFullCardsByName(name: string, page: number, pageSize: number) {
    let params = new HttpParams();
    params = params.append('name', name);
    params = params.append('page', String(page));
    params = params.append('pageSize', String(pageSize));
    return this.apiGetRequestWithCriteria(FULL_URL, params);
  }

  /**
   * Get the number of cards that their names matches with the specified one
   * @param name the name to filter
   */
  countCardsByName(name: string) {
    return this.apiGetRequest(SIZE_URL + '?name=' + encodeURIComponent(name));
  }

  /**
   * Get all existing sets
   */
  getAllSets() {
    return this.apiGetRequest(SET_URL);
  }

  /**
   * Get all cards which attributes matches with the given criteria
   * @param criteria the card criteria to filter by
   * @param page page index
   */
  getCardsByCriteria(criteria: HttpParams) {
    return this.apiGetRequestWithCriteria(BASIC_URL, criteria);
  }

  getCardInfo(id: number) {
    return this.apiGetRequest(CARD_INFO_URL + id);
  }
}

