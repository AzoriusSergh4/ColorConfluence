import {Injectable} from '@angular/core';
import {CommonService} from './common.service';
import {HttpClient, HttpHeaders, HttpParams} from '@angular/common/http';
import {DeckForm} from '../deck-creation/deck-creation.component';

const FORMAT_URL = '/api/format';
const DECK_URL = '/api/deck';
const DECKS_URL = DECK_URL + '/decks';
const DECK_MOVE_URL = DECK_URL + '/move';
const CREATE_URL = DECK_URL + '/create';
const ALL_URL = FORMAT_URL + '/all';
const FOLDER_URL = DECK_URL + '/folder';
const FOLDER_MOVE_URL = DECK_URL + '/folder/move';

@Injectable({
  providedIn: 'root'
})
export class DeckService extends CommonService {

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
    return this.apiGetRequest(DECKS_URL + '?page=' + page);
  }

  /**
   * Get a page of decks with the specified criteria
   * @param criteria the criteria to filter
   */
  getAllDecksWithCriteria(criteria: HttpParams) {
    return this.apiGetRequestWithCriteria(DECKS_URL, criteria);
  }

  /**
   * Get all decks from the given user
   * @param userId the id of the user
   */
  getUserDecks(userId: number) {
    return this.apiGetRequest(DECKS_URL + '/' + userId);
  }

  /**
   * Save the deck
   * @param deckForm the deck data
   */
  saveDeck(deckForm: DeckForm) {
    const body = JSON.stringify(deckForm);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.apiPostRequest(CREATE_URL, body, headers);
  }

  /**
   * Deletes the deck
   * @param id the deck id
   */
  deleteDeck(id: number) {
    return this.apiDeleteRequest(DECK_URL + '/' + id);
  }

  /**
   * Move a deck
   * @param deckId the deck id
   * @param toFolder the destination folder
   */
  moveDeck(deckId: number, toFolder: number) {
    const object = {
      fromId: deckId,
      toId: toFolder
    };
    const body = JSON.stringify(object);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.apiPutRequest(DECK_MOVE_URL, body, headers);
  }

  /**
   * Creates a folder
   * @param parent the parent folder id
   * @param folderName the name of the folder
   */
  newFolder(parent: number, folderName: string) {
    const object = {
      parentId: parent,
      name: folderName
    };
    const body = JSON.stringify(object);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.apiPostRequest(FOLDER_URL, body, headers);
  }

  /**
   * Delete a folder
   * @param id the id of the folder to delete
   */
  deleteFolder(id: number) {
    const object = {
      folderId: id
    };
    const body = JSON.stringify(object);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });
    return this.apiDeleteRequest(FOLDER_URL, body, headers);
  }

  /**
   * Move a folder
   * @param fromFolder the source folder
   * @param toFolder the destination folder
   */
  moveFolder(fromFolder: number, toFolder: number) {
    const object = {
      folderId: fromFolder,
      toId: toFolder
    };
    const body = JSON.stringify(object);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    return this.apiPutRequest(FOLDER_MOVE_URL, body, headers);
  }

  /**
   * Get all formats
   */
  getAllFormats() {
    return this.apiGetRequest(ALL_URL);
  }
}
