import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PopupCatalogueComponent } from './popup-catalogue.component';

describe('PopupCatalogueComponent', () => {
  let component: PopupCatalogueComponent;
  let fixture: ComponentFixture<PopupCatalogueComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PopupCatalogueComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PopupCatalogueComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
