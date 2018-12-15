import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeducteRecordDetailComponent } from './deducte-record-detail.component';

describe('DeducteRecordDetailComponent', () => {
  let component: DeducteRecordDetailComponent;
  let fixture: ComponentFixture<DeducteRecordDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeducteRecordDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeducteRecordDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
