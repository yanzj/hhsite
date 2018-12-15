import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentAccountDetailComponent } from './repayment-account-detail.component';

describe('RepaymentAccountDetailComponent', () => {
  let component: RepaymentAccountDetailComponent;
  let fixture: ComponentFixture<RepaymentAccountDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentAccountDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentAccountDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
