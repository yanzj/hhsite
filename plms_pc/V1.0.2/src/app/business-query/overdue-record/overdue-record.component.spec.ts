import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OverdueRecordComponent } from './overdue-record.component';

describe('OverdueRecordComponent', () => {
  let component: OverdueRecordComponent;
  let fixture: ComponentFixture<OverdueRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OverdueRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OverdueRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
