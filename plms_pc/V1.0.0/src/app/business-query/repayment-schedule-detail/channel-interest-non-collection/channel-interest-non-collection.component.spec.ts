import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelInterestNonCollectionComponent } from './channel-interest-non-collection.component';

describe('ChannelInterestNonCollectionComponent', () => {
  let component: ChannelInterestNonCollectionComponent;
  let fixture: ComponentFixture<ChannelInterestNonCollectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChannelInterestNonCollectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannelInterestNonCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
