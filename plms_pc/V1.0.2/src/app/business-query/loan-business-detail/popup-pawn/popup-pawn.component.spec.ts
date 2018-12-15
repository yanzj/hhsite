import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupPawnComponent } from './popup-pawn.component';

describe('PopupPawnComponent', () => {
  let component: PopupPawnComponent;
  let fixture: ComponentFixture<PopupPawnComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupPawnComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupPawnComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
