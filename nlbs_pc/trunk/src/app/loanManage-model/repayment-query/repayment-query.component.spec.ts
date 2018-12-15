import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentQueryComponent } from './repayment-query.component';

describe('RepaymentQueryComponent', () => {
  let component: RepaymentQueryComponent;
  let fixture: ComponentFixture<RepaymentQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
