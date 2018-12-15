import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentPlanDetailComponent } from './repayment-plan-detail.component';

describe('RepaymentPlanDetailComponent', () => {
  let component: RepaymentPlanDetailComponent;
  let fixture: ComponentFixture<RepaymentPlanDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentPlanDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentPlanDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
