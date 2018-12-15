import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { CheckRepaymentComponent } from './check-repayment.component';

describe('CheckRepaymentComponent', () => {
  let component: CheckRepaymentComponent;
  let fixture: ComponentFixture<CheckRepaymentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ CheckRepaymentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(CheckRepaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
