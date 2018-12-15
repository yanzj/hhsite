import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SomeApprovalInfoComponent } from './some-approval-info.component';

describe('SomeApprovalInfoComponent', () => {
  let component: SomeApprovalInfoComponent;
  let fixture: ComponentFixture<SomeApprovalInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SomeApprovalInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SomeApprovalInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
