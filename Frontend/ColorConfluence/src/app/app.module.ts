import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HeaderComponent } from './components/header/header.component';
import { MenuPrincipalComponent } from './components/menu-principal/menu-principal.component';
import { BuscadorComponent } from './components/buscador/buscador.component';
import { CalculadoraManaComponent } from './components/calculadora-mana/calculadora-mana.component';
import { GestorMazosComponent } from './components/gestor-mazos/gestor-mazos.component';
import { ErrorComponent } from './components/error/error/error.component';

@NgModule({
  declarations: [
    AppComponent,
    HeaderComponent,
    MenuPrincipalComponent,
    BuscadorComponent,
    CalculadoraManaComponent,
    GestorMazosComponent,
    ErrorComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
