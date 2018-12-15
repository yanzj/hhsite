import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SelectLoanContractComponent } from './select-loan-contract.component';

describe('SelectLoanContractComponent', () => {
  let component: SelectLoanContractComponent;
  let fixture: ComponentFixture<SelectLoanContractComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SelectLoanContractComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SelectLoanContractComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
