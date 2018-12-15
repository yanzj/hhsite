import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { InterestCollectionListComponent } from './interest-collection-list.component';

describe('InterestCollectionListComponent', () => {
  let component: InterestCollectionListComponent;
  let fixture: ComponentFixture<InterestCollectionListComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ InterestCollectionListComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(InterestCollectionListComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
