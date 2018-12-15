import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApprovalInformationComponent } from './approval-information.component';

describe('ApprovalInformationComponent', () => {
  let component: ApprovalInformationComponent;
  let fixture: ComponentFixture<ApprovalInformationComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApprovalInformationComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApprovalInformationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
