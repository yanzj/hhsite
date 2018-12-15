import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ApplyDetailComponent } from './apply-detail.component';

describe('ApplyDetailComponent', () => {
  let component: ApplyDetailComponent;
  let fixture: ComponentFixture<ApplyDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ApplyDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ApplyDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
