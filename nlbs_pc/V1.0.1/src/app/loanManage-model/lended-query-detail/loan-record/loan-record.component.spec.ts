import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanRecordComponent } from './loan-record.component';

describe('LoanRecordComponent', () => {
  let component: LoanRecordComponent;
  let fixture: ComponentFixture<LoanRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
