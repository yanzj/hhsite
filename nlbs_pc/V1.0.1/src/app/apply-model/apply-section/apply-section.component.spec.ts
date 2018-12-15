import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplySectionComponent } from './apply-section.component';

describe('ApplySectionComponent', () => {
  let component: ApplySectionComponent;
  let fixture: ComponentFixture<ApplySectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplySectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplySectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
