import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CalculadoraManaComponent } from './calculadora-mana.component';

describe('CalculadoraManaComponent', () => {
  let component: CalculadoraManaComponent;
  let fixture: ComponentFixture<CalculadoraManaComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CalculadoraManaComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CalculadoraManaComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
