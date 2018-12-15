import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupAddRepaymentComponent } from './popup-add-repayment.component';

describe('PopupAddRepaymentComponent', () => {
  let component: PopupAddRepaymentComponent;
  let fixture: ComponentFixture<PopupAddRepaymentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupAddRepaymentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupAddRepaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
