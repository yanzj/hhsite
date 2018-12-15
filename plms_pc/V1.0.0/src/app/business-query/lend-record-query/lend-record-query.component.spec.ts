import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LendRecordQueryComponent } from './lend-record-query.component';

describe('LendRecordQueryComponent', () => {
  let component: LendRecordQueryComponent;
  let fixture: ComponentFixture<LendRecordQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LendRecordQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LendRecordQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
