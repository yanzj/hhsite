import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterestUnagentComponent } from './interest-unagent.component';

describe('InterestUnagentComponent', () => {
  let component: InterestUnagentComponent;
  let fixture: ComponentFixture<InterestUnagentComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterestUnagentComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterestUnagentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
