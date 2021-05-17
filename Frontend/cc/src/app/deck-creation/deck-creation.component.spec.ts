import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DeckCreationComponent } from './deck-creation.component';

describe('DeckCreationComponent', () => {
  let component: DeckCreationComponent;
  let fixture: ComponentFixture<DeckCreationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DeckCreationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(DeckCreationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
