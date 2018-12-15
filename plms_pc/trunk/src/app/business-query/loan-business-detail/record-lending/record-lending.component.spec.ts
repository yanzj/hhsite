import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordLendingComponent } from './record-lending.component';

describe('RecordLendingComponent', () => {
  let component: RecordLendingComponent;
  let fixture: ComponentFixture<RecordLendingComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecordLendingComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordLendingComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
