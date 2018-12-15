import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PersonEnquiryComponent } from './person-enquiry.component';

describe('PersonEnquiryComponent', () => {
  let component: PersonEnquiryComponent;
  let fixture: ComponentFixture<PersonEnquiryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PersonEnquiryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PersonEnquiryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
