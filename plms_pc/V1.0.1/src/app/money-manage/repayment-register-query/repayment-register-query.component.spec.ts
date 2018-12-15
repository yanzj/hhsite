import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentRegisterQueryComponent } from './repayment-register-query.component';

describe('RepaymentRegisterQueryComponent', () => {
  let component: RepaymentRegisterQueryComponent;
  let fixture: ComponentFixture<RepaymentRegisterQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentRegisterQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentRegisterQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
