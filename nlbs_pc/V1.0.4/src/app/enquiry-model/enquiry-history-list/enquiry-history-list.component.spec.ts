import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnquiryHistoryListComponent } from './enquiry-history-list.component';

describe('EnquiryHistoryListComponent', () => {
  let component: EnquiryHistoryListComponent;
  let fixture: ComponentFixture<EnquiryHistoryListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnquiryHistoryListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnquiryHistoryListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
