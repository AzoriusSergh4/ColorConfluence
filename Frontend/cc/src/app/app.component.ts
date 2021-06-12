import {Component} from '@angular/core';
import {Router} from '@angular/router';
import {MatIconRegistry} from '@angular/material/icon';
import {DomSanitizer} from '@angular/platform-browser';
import {sanitizeIdentifier} from '@angular/compiler';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  title = 'cc';

  constructor(protected router: Router, iconRegistry: MatIconRegistry, private sanitizer: DomSanitizer) {
    // Numbers
    iconRegistry.addSvgIcon('{0}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/0.svg'));
    iconRegistry.addSvgIcon('{1}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/1.svg'));
    iconRegistry.addSvgIcon('{2}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/2.svg'));
    iconRegistry.addSvgIcon('{3}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/3.svg'));
    iconRegistry.addSvgIcon('{4}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/4.svg'));
    iconRegistry.addSvgIcon('{5}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/5.svg'));
    iconRegistry.addSvgIcon('{6}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/6.svg'));
    iconRegistry.addSvgIcon('{7}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/7.svg'));
    iconRegistry.addSvgIcon('{8}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/8.svg'));
    iconRegistry.addSvgIcon('{9}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/9.svg'));
    iconRegistry.addSvgIcon('{10}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/10.svg'));
    iconRegistry.addSvgIcon('{11}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/11.svg'));
    iconRegistry.addSvgIcon('{12}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/12.svg'));
    iconRegistry.addSvgIcon('{13}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/13.svg'));
    iconRegistry.addSvgIcon('{14}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/14.svg'));
    iconRegistry.addSvgIcon('{15}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/15.svg'));
    iconRegistry.addSvgIcon('{16}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/16.svg'));
    iconRegistry.addSvgIcon('{17}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/17.svg'));
    iconRegistry.addSvgIcon('{18}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/18.svg'));
    iconRegistry.addSvgIcon('{19}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/19.svg'));
    iconRegistry.addSvgIcon('{20}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/20.svg'));
    // Colors
    iconRegistry.addSvgIcon('{W}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/white.svg'));
    iconRegistry.addSvgIcon('{U}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/blue.svg'));
    iconRegistry.addSvgIcon('{B}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/black.svg'));
    iconRegistry.addSvgIcon('{R}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/red.svg'));
    iconRegistry.addSvgIcon('{G}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/green.svg'));
    iconRegistry.addSvgIcon('{C}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/colorless.svg'));
    // Colors or 2
    iconRegistry.addSvgIcon('{2/B}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/2B.svg'));
    iconRegistry.addSvgIcon('{2/W}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/2W.svg'));
    iconRegistry.addSvgIcon('{2/U}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/2U.svg'));
    iconRegistry.addSvgIcon('{2/R}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/2R.svg'));
    iconRegistry.addSvgIcon('{2/G}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/2G.svg'));
    // Phyrexian
    iconRegistry.addSvgIcon('{W/P}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/WP.svg'));
    iconRegistry.addSvgIcon('{U/P}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/UP.svg'));
    iconRegistry.addSvgIcon('{B/P}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/BP.svg'));
    iconRegistry.addSvgIcon('{R/P}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/RP.svg'));
    iconRegistry.addSvgIcon('{G/P}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/GP.svg'));
    // Bi Color
    iconRegistry.addSvgIcon('{B/G}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/BG.svg'));
    iconRegistry.addSvgIcon('{B/R}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/BR.svg'));
    iconRegistry.addSvgIcon('{G/U}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/GU.svg'));
    iconRegistry.addSvgIcon('{G/W}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/GW.svg'));
    iconRegistry.addSvgIcon('{R/G}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/RG.svg'));
    iconRegistry.addSvgIcon('{R/W}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/RW.svg'));
    iconRegistry.addSvgIcon('{U/B}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/UB.svg'));
    iconRegistry.addSvgIcon('{U/R}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/UR.svg'));
    iconRegistry.addSvgIcon('{W/B}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/WB.svg'));
    iconRegistry.addSvgIcon('{W/U}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/WU.svg'));
    // Other
    iconRegistry.addSvgIcon('{S}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/S.svg'));
    iconRegistry.addSvgIcon('{X}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/X.svg'));
    iconRegistry.addSvgIcon('{T}', sanitizer.bypassSecurityTrustResourceUrl('assets/icons/T.svg'));
  }
}
