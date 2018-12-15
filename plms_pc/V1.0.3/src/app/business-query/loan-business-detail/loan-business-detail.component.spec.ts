import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LoanBusinessDetailComponent } from './loan-business-detail.component';

describe('LoanBusinessDetailComponent', () => {
  let component: LoanBusinessDetailComponent;
  let fixture: ComponentFixture<LoanBusinessDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LoanBusinessDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LoanBusinessDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
