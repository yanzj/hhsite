import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RepayScheduleAgentComponent } from './repay-schedule-agent.component';

describe('RepayScheduleAgentComponent', () => {
  let component: RepayScheduleAgentComponent;
  let fixture: ComponentFixture<RepayScheduleAgentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RepayScheduleAgentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RepayScheduleAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
