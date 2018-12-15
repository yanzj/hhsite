import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupPersonInfoComponent } from './popup-person-info.component';

describe('PopupPesrsonInfoComponent', () => {
  let component: PopupPersonInfoComponent;
  let fixture: ComponentFixture<PopupPersonInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupPersonInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupPersonInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
