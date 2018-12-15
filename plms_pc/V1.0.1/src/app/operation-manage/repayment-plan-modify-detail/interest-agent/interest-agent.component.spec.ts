import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterestAgentComponent } from './interest-agent.component';

describe('InterestAgentComponent', () => {
  let component: InterestAgentComponent;
  let fixture: ComponentFixture<InterestAgentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterestAgentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterestAgentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
