import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentScheduleDetailComponent } from './repayment-schedule-detail.component';

describe('RepaymentScheduleDetailComponent', () => {
  let component: RepaymentScheduleDetailComponent;
  let fixture: ComponentFixture<RepaymentScheduleDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentScheduleDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentScheduleDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
