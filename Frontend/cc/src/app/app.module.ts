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
import {DragDropModule} from '@angular/cdk/drag-drop';
import {MatSnackBarModule} from '@angular/material/snack-bar';
import {MatSortModule} from '@angular/material/sort';
import {MatTableModule} from '@angular/material/table';
import {MatDialogModule} from '@angular/material/dialog';
import {MatTabsModule} from '@angular/material/tabs';
import {MatProgressSpinnerModule} from '@angular/material/progress-spinner';

import {FormsModule, ReactiveFormsModule} from '@angular/forms';

import {FlexLayoutModule} from '@angular/flex-layout';

import {CovalentLayoutModule} from '@covalent/core/layout';
import {CovalentStepsModule} from '@covalent/core/steps';
import { CovalentPagingModule } from '@covalent/core/paging';
import { CovalentBarEchartsModule } from '@covalent/echarts/bar';
import { CovalentPieEchartsModule } from '@covalent/echarts/pie';
import { CovalentToolboxEchartsModule } from '@covalent/echarts/toolbox';
import { CovalentTooltipEchartsModule } from '@covalent/echarts/tooltip';
import { CovalentDataTableModule } from '@covalent/core/data-table';
/* any other core modules */
import {CovalentHighlightModule} from '@covalent/highlight';
import {CovalentMarkdownModule} from '@covalent/markdown';
import {CovalentDynamicFormsModule} from '@covalent/dynamic-forms';
import {CovalentBaseEchartsModule} from '@covalent/echarts/base';
import { CovalentUserProfileModule } from '@covalent/core/user-profile';
import {CardService} from './services/card.service';
import {MatCheckboxModule} from '@angular/material/checkbox';
import {MatSelectModule} from '@angular/material/select';
import {MatTooltipModule} from '@angular/material/tooltip';
import { CardInformationComponent } from './card-information/card-information.component';
import {MatPaginatorModule} from '@angular/material/paginator';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ConfirmAccountComponent } from './confirm-account/confirm-account.component';
import {HTTP_INTERCEPTORS} from '@angular/common/http';
import {BasicAuthInterceptor} from './services/auth/auth.interceptor';
import {LoginService} from './services/login.service';
import { ProfileComponent } from './profile/profile.component';
import {MatSlideToggleModule} from '@angular/material/slide-toggle';
import { BaseComponent } from './base/base.component';
import { RecoverPasswordComponent } from './recover-password/recover-password.component';
import { RecoverPasswordFormComponent } from './recover-password-form/recover-password-form.component';
import {DeckCreationComponent, SaveDeckDialog} from './deck-creation/deck-creation.component';
import { DeckComponent } from './deck/deck.component';
import {DeckService} from './services/deck.service';
import { DeckListComponent } from './deck-list/deck-list.component';

const appRoutes = [
  {path: '', component: MainScreenComponent},
  {path: 'cards', component: CardSearchComponent},
  {path: 'card/:id', component: CardInformationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'confirm-account', component: ConfirmAccountComponent},
  {path: 'recover-password', component: RecoverPasswordComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'create-deck', component: DeckCreationComponent},
  {path: 'decks', component: DeckListComponent},
  {path: 'deck/:id', component: DeckComponent}
];


@NgModule({
  declarations: [
    AppComponent,
    MainMenuComponent,
    MainScreenComponent,
    CardSearchComponent,
    CardInformationComponent,
    LoginComponent,
    RegisterComponent,
    ConfirmAccountComponent,
    ProfileComponent,
    BaseComponent,
    RecoverPasswordComponent,
    RecoverPasswordFormComponent,
    DeckCreationComponent,
    DeckComponent,
    SaveDeckDialog,
    DeckListComponent
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
    MatPaginatorModule,
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
    CovalentBarEchartsModule,
    CovalentPieEchartsModule,
    CovalentToolboxEchartsModule,
    CovalentTooltipEchartsModule,
    CovalentDataTableModule,
    MatCheckboxModule,
    MatSelectModule,
    FormsModule,
    MatTooltipModule,
    MatSlideToggleModule,
    CovalentUserProfileModule,
    MatSnackBarModule,
    DragDropModule,
    MatSortModule,
    MatTableModule,
    MatDialogModule,
    CovalentPagingModule,
    MatTabsModule,
    MatProgressSpinnerModule
  ],
  providers: [
    CardService,
    LoginService,
    DeckService,
    { provide: HTTP_INTERCEPTORS, useClass: BasicAuthInterceptor, multi: true }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
