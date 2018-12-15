import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OverdueRecordDetailComponent } from './overdue-record-detail.component';

describe('OverdueRecordDetailComponent', () => {
  let component: OverdueRecordDetailComponent;
  let fixture: ComponentFixture<OverdueRecordDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OverdueRecordDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OverdueRecordDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
