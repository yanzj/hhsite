import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ChannelInterestCollectionComponent } from './channel-interest-collection.component';

describe('ChannelInterestCollectionComponent', () => {
  let component: ChannelInterestCollectionComponent;
  let fixture: ComponentFixture<ChannelInterestCollectionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ChannelInterestCollectionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ChannelInterestCollectionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
