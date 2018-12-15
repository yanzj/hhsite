import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupBorrowerComponent } from './popup-borrower.component';

describe('PopupBorrowerComponent', () => {
  let component: PopupBorrowerComponent;
  let fixture: ComponentFixture<PopupBorrowerComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupBorrowerComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupBorrowerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
