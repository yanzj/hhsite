import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterestNonCollectionListComponent } from './interest-non-collection-list.component';

describe('InterestNonCollectionListComponent', () => {
  let component: InterestNonCollectionListComponent;
  let fixture: ComponentFixture<InterestNonCollectionListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterestNonCollectionListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterestNonCollectionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
