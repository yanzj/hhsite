import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentPlanModifyDetailComponent } from './repayment-plan-modify-detail.component';

describe('RepaymentPlanModifyDetailComponent', () => {
  let component: RepaymentPlanModifyDetailComponent;
  let fixture: ComponentFixture<RepaymentPlanModifyDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentPlanModifyDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentPlanModifyDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
