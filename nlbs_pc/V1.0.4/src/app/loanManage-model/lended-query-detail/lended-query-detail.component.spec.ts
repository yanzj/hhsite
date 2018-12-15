import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LendedQueryDetailComponent } from './lended-query-detail.component';

describe('LendedQueryDetailComponent', () => {
  let component: LendedQueryDetailComponent;
  let fixture: ComponentFixture<LendedQueryDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LendedQueryDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LendedQueryDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
