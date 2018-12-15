import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentRecordViewComponent } from './repayment-record-view.component';

describe('RepaymentRecordViewComponent', () => {
  let component: RepaymentRecordViewComponent;
  let fixture: ComponentFixture<RepaymentRecordViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentRecordViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentRecordViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
