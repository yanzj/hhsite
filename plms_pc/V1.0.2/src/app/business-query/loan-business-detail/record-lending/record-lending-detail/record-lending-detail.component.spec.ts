import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RecordLendingDetailComponent } from './record-lending-detail.component';

describe('RecordLendingDetailComponent', () => {
  let component: RecordLendingDetailComponent;
  let fixture: ComponentFixture<RecordLendingDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RecordLendingDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RecordLendingDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
