import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplyMaterialComponent } from './apply-material.component';

describe('ApplyMaterialComponent', () => {
  let component: ApplyMaterialComponent;
  let fixture: ComponentFixture<ApplyMaterialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplyMaterialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplyMaterialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
