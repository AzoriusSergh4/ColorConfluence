import {ComponentFixture, TestBed} from '@angular/core/testing';

import {DeckUserComponent} from './deck-user.component';

describe('DeckUserComponent', () => {
  let component: DeckUserComponent;
  let fixture: ComponentFixture<DeckUserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeckUserComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeckUserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
