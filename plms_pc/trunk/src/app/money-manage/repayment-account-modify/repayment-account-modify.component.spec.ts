import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentAccountModifyComponent } from './repayment-account-modify.component';

describe('RepaymentAccountModifyComponent', () => {
  let component: RepaymentAccountModifyComponent;
  let fixture: ComponentFixture<RepaymentAccountModifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentAccountModifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentAccountModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
