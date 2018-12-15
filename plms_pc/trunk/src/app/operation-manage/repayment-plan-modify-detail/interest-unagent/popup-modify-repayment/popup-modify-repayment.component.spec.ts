import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupModifyRepaymentComponent } from './popup-modify-repayment.component';

describe('PopupModifyRepaymentComponent', () => {
  let component: PopupModifyRepaymentComponent;
  let fixture: ComponentFixture<PopupModifyRepaymentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupModifyRepaymentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupModifyRepaymentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
