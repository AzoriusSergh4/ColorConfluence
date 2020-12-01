import {Injectable} from '@angular/core';
import {HttpClient} from '@angular/common/http';
import {CommonService} from './common.service';

const CARD_URL = '/api/card';
const FIND_NAME = CARD_URL + '/find/name';

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
    return this.apiGetRequest(FIND_NAME + '?name=' + name);
  }
}
