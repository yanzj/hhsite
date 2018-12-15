import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { EnquiryResubmitComponent } from './enquiry-resubmit.component';

describe('EnquiryResubmitComponent', () => {
  let component: EnquiryResubmitComponent;
  let fixture: ComponentFixture<EnquiryResubmitComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ EnquiryResubmitComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(EnquiryResubmitComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
