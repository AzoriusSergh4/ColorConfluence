import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MenuPrincipalComponent } from './components/menu-principal/menu-principal.component';
import { ErrorComponent } from './components/error/error/error.component';


const routes: Routes = [];

@NgModule({
  imports: [RouterModule.forRoot([
    { path: '', component: MenuPrincipalComponent},
    { path: '**', component: ErrorComponent }
  ])],
  exports: [RouterModule]
})
export class AppRoutingModule { }
