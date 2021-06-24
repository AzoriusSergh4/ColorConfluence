import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainScreenComponent} from './main-screen/main-screen.component';
import {CardSearchComponent} from './card-search/card-search.component';
import {CardInformationComponent} from './card-information/card-information.component';
import {LoginComponent} from './login/login.component';
import {RegisterComponent} from './register/register.component';
import {ConfirmAccountComponent} from './confirm-account/confirm-account.component';
import {RecoverPasswordComponent} from './recover-password/recover-password.component';
import {ProfileComponent} from './profile/profile.component';
import {DeckCreationComponent} from './deck-creation/deck-creation.component';
import {DeckListComponent} from './deck-list/deck-list.component';
import {DeckComponent} from './deck/deck.component';
import {DeckUserComponent} from './deck-user/deck-user.component';


const routes: Routes = [
  {path: '', component: MainScreenComponent},
  {path: 'cards', component: CardSearchComponent},
  {path: 'card/:id', component: CardInformationComponent},
  {path: 'login', component: LoginComponent},
  {path: 'register', component: RegisterComponent},
  {path: 'confirm-account', component: ConfirmAccountComponent},
  {path: 'recover-password', component: RecoverPasswordComponent},
  {path: 'profile', component: ProfileComponent},
  {path: 'deck-editor', component: DeckCreationComponent},
  {path: 'decks', component: DeckListComponent},
  {path: 'deck/:id', component: DeckComponent},
  {path: 'user-decks', component: DeckUserComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
