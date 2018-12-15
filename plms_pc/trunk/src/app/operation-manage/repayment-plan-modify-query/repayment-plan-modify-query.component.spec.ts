import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentPlanModifyQueryComponent } from './repayment-plan-modify-query.component';

describe('RepaymentPlanModifyQueryComponent', () => {
  let component: RepaymentPlanModifyQueryComponent;
  let fixture: ComponentFixture<RepaymentPlanModifyQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentPlanModifyQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentPlanModifyQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
