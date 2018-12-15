import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DebitRecordComponent } from './debit-record.component';

describe('DebitRecordComponent', () => {
  let component: DebitRecordComponent;
  let fixture: ComponentFixture<DebitRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DebitRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DebitRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
