import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupJudicialComponent } from './popup-judicial.component';

describe('PopupJudicialComponent', () => {
  let component: PopupJudicialComponent;
  let fixture: ComponentFixture<PopupJudicialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupJudicialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupJudicialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
