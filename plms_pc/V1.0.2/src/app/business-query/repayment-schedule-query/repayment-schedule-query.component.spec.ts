import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentScheduleQueryComponent } from './repayment-schedule-query.component';

describe('RepaymentScheduleQueryComponent', () => {
  let component: RepaymentScheduleQueryComponent;
  let fixture: ComponentFixture<RepaymentScheduleQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentScheduleQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentScheduleQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
