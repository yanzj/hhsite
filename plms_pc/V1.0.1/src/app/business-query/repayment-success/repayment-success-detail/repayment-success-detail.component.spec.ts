import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentSuccessDetailComponent } from './repayment-success-detail.component';

describe('RepaymentSuccessDetailComponent', () => {
  let component: RepaymentSuccessDetailComponent;
  let fixture: ComponentFixture<RepaymentSuccessDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentSuccessDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentSuccessDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
