import {Injectable} from '@angular/core';


@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  readonly regex = '{[0-9A-Z/]+}';

  constructor() {
  }

  public getManaSymbols(text: string): string[] {
    if (text) {
      return text.match(new RegExp(this.regex, 'g'));
    } else {
      return [];
    }
  }

  public getManaSymbolsReversed(text: string) {
    return this.getManaSymbols(text).reverse();
  }
}
