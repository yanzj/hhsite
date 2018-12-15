import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupModifyComponent } from './popup-modify.component';

describe('PopupModifyComponent', () => {
  let component: PopupModifyComponent;
  let fixture: ComponentFixture<PopupModifyComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupModifyComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupModifyComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
