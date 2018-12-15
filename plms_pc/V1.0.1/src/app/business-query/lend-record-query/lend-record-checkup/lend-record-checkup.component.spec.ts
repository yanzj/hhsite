import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LendRecordCheckupComponent } from './lend-record-checkup.component';

describe('LendRecordCheckupComponent', () => {
  let component: LendRecordCheckupComponent;
  let fixture: ComponentFixture<LendRecordCheckupComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LendRecordCheckupComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LendRecordCheckupComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
