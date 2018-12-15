import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentPlanConfirmationComponent } from './repayment-plan-confirmation.component';

describe('RepaymentPlanConfirmationComponent', () => {
  let component: RepaymentPlanConfirmationComponent;
  let fixture: ComponentFixture<RepaymentPlanConfirmationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentPlanConfirmationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentPlanConfirmationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
