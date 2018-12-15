import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OverdueQueryComponent } from './overdue-query.component';

describe('OverdueQueryComponent', () => {
  let component: OverdueQueryComponent;
  let fixture: ComponentFixture<OverdueQueryComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OverdueQueryComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OverdueQueryComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
