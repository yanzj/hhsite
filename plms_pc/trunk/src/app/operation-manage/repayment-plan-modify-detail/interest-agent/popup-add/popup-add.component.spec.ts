import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupAddComponent } from './popup-add.component';

describe('PopupAddComponent', () => {
  let component: PopupAddComponent;
  let fixture: ComponentFixture<PopupAddComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupAddComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
