import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { OverdueDetailComponent } from './overdue-detail.component';

describe('OverdueDetailComponent', () => {
  let component: OverdueDetailComponent;
  let fixture: ComponentFixture<OverdueDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ OverdueDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(OverdueDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
