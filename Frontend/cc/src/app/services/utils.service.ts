import { Injectable } from '@angular/core';
import {DomSanitizer, SafeHtml} from '@angular/platform-browser';

@Injectable({
  providedIn: 'root'
})
export class UtilsService {

  readonly regex = '{[0-9A-Z/]+}';

  constructor(private sanitizer: DomSanitizer) { }

  public replaceManaSymbols(text: string): SafeHtml {
    if (text && text.match(new RegExp(this.regex, 'g'))) {
      text.match(new RegExp(this.regex, 'g')).filter(function(element, index, array) {
        return index === array.indexOf(element);
      }).forEach(m => {
        // console.log('<span><mat-icon svgIcon="' + m + '" aria-hidden="false"></mat-icon></span>');
        text = text.replace(m, '<span><mat-icon svgIcon="' + m + '" aria-hidden="false"></mat-icon></span>');
      });

    }
    return this.sanitizer.bypassSecurityTrustHtml(text);
  }

  public getManaSymbols(text: string): string[] {
    if (text){
      return text.match(new RegExp(this.regex, 'g'));
    } else {
      return [];
    }
  }
}
