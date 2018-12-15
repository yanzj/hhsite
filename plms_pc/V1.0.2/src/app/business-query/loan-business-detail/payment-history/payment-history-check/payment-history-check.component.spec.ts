import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PaymentHistoryCheckComponent } from './payment-history-check.component';

describe('PaymentHistoryCheckComponent', () => {
  let component: PaymentHistoryCheckComponent;
  let fixture: ComponentFixture<PaymentHistoryCheckComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PaymentHistoryCheckComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PaymentHistoryCheckComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
