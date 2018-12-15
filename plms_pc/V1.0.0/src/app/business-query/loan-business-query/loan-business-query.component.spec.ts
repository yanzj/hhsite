import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanBusinessQueryComponent } from './loan-business-query.component';

describe('LoanBusinessQueryComponent', () => {
  let component: LoanBusinessQueryComponent;
  let fixture: ComponentFixture<LoanBusinessQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanBusinessQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanBusinessQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
