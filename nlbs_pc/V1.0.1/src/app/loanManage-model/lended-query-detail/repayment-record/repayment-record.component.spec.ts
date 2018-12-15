import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentRecordComponent } from './repayment-record.component';

describe('RepaymentRecordComponent', () => {
  let component: RepaymentRecordComponent;
  let fixture: ComponentFixture<RepaymentRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
