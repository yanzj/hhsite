import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupPawnDetailComponent } from './popup-pawn-detail.component';

describe('PopupPawnDetailComponent', () => {
  let component: PopupPawnDetailComponent;
  let fixture: ComponentFixture<PopupPawnDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupPawnDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupPawnDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
