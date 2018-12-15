import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplyInfoComponent } from './apply-info.component';

describe('ApplyInfoComponent', () => {
  let component: ApplyInfoComponent;
  let fixture: ComponentFixture<ApplyInfoComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplyInfoComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplyInfoComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
