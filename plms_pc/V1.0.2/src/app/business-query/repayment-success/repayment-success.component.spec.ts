import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentSuccessComponent } from './repayment-success.component';

describe('RepaymentSuccessComponent', () => {
  let component: RepaymentSuccessComponent;
  let fixture: ComponentFixture<RepaymentSuccessComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentSuccessComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentSuccessComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
