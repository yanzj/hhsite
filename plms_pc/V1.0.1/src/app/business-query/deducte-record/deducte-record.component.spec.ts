import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DeducteRecordComponent } from './deducte-record.component';

describe('DeducteRecordComponent', () => {
  let component: DeducteRecordComponent;
  let fixture: ComponentFixture<DeducteRecordComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DeducteRecordComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DeducteRecordComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
