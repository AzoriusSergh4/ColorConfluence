import {BrowserModule} from '@angular/platform-browser';
import {NgModule} from '@angular/core';

import {RouterModule} from '@angular/router';

import {AppRoutingModule} from './app-routing.module';
import {AppComponent} from './app.component';
import {MainMenuComponent} from './main-menu/main-menu.component';
import {BrowserAnimationsModule} from '@angular/platform-browser/animations';

import {ScrollingModule} from '@angular/cdk/scrolling';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatCardModule} from '@angular/material/card';
import {MatGridListModule} from '@angular/material/grid-list';
import {MatListModule} from '@angular/material/list';
import {MainScreenComponent} from './main-screen/main-screen.component';
import {CardSearchComponent} from './card-search/card-search.component';
import {MatMenuModule} from '@angular/material/menu';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';
import {MatDividerModule} from '@angular/material/divider';
import {LayoutModule} from '@angular/cdk/layout';
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatInputModule} from '@angular/material/input';
import {MatExpansionModule} from '@angular/material/expansion';
import {MatChipsModule} from '@angular/material/chips';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {FlexLayoutModule} from '@angular/flex-layout';

import {CovalentLayoutModule} from '@covalent/core/layout';
import {CovalentStepsModule} from '@covalent/core/steps';
/* any other core modules */
import {CovalentHighlightModule} from '@covalent/highlight';
import {CovalentMarkdownModule} from '@covalent/markdown';
import {CovalentDynamicFormsModule} from '@covalent/dynamic-forms';
import {CovalentBaseEchartsModule} from '@covalent/echarts/base';
import {CardService} from './services/card.service';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSelectModule} from '@angular/material/select';
import {MatTooltipModule} from '@angular/material/tooltip';
import { CardInformationComponent } from './card-information/card-information.component';

const appRoutes = [
  {path: '', component: MainScreenComponent},
  {path: 'cards', component: CardSearchComponent},
  {path: 'card/:id', component: CardInformationComponent}
];


@NgModule({
  declarations: [
    AppComponent,
    MainMenuComponent,
    MainScreenComponent,
    CardSearchComponent,
    CardInformationComponent
  ],
  imports: [
    RouterModule.forRoot(appRoutes),
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatToolbarModule,
    MatCardModule,
    MatGridListModule,
    MatListModule,
    MatMenuModule,
    MatIconModule,
    MatButtonModule,
    MatExpansionModule,
    MatChipsModule,
    LayoutModule,
    MatDividerModule,
    ScrollingModule,
    ReactiveFormsModule,
    MatFormFieldModule,
    MatInputModule,
    FlexLayoutModule,
    CovalentLayoutModule,
    CovalentStepsModule,
    CovalentHighlightModule,
    CovalentMarkdownModule,
    CovalentDynamicFormsModule,
    CovalentBaseEchartsModule,
    MatCheckboxModule,
    MatSelectModule,
    FormsModule,
    MatTooltipModule
  ],
  providers: [CardService],
  bootstrap: [AppComponent]
})
export class AppModule { }
