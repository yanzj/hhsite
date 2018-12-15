import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { LendedQueryComponent } from './lended-query.component';

describe('LendedQueryComponent', () => {
  let component: LendedQueryComponent;
  let fixture: ComponentFixture<LendedQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ LendedQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(LendedQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
