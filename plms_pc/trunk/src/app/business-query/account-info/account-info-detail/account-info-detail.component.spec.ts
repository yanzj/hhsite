import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { AccountInfoDetailComponent } from './account-info-detail.component';

describe('AccountInfoDetailComponent', () => {
  let component: AccountInfoDetailComponent;
  let fixture: ComponentFixture<AccountInfoDetailComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ AccountInfoDetailComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(AccountInfoDetailComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
