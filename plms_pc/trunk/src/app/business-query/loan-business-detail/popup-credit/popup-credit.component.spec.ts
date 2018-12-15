import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupCreditComponent } from './popup-credit.component';

describe('PopupCreditComponent', () => {
  let component: PopupCreditComponent;
  let fixture: ComponentFixture<PopupCreditComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupCreditComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupCreditComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
