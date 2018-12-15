import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfessionalSerialComponent } from './professional-serial.component';

describe('ProfessionalSerialComponent', () => {
  let component: ProfessionalSerialComponent;
  let fixture: ComponentFixture<ProfessionalSerialComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ProfessionalSerialComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfessionalSerialComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
