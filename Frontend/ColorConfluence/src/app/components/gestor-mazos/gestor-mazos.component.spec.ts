import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GestorMazosComponent } from './gestor-mazos.component';

describe('GestorMazosComponent', () => {
  let component: GestorMazosComponent;
  let fixture: ComponentFixture<GestorMazosComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GestorMazosComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GestorMazosComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
