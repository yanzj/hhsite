import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanRecordViewComponent } from './loan-record-view.component';

describe('LoanRecordViewComponent', () => {
  let component: LoanRecordViewComponent;
  let fixture: ComponentFixture<LoanRecordViewComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanRecordViewComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanRecordViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
