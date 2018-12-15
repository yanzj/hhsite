import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepaymentDetailComponent } from './repayment-detail.component';

describe('RepaymentDetailComponent', () => {
  let component: RepaymentDetailComponent;
  let fixture: ComponentFixture<RepaymentDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepaymentDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepaymentDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
