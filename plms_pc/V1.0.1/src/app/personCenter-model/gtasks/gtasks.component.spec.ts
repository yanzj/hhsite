import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { GtasksComponent } from './gtasks.component';

describe('GtasksComponent', () => {
  let component: GtasksComponent;
  let fixture: ComponentFixture<GtasksComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ GtasksComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(GtasksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
