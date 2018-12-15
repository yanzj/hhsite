import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { JusticeMortgageComponent } from './justice-mortgage.component';

describe('JusticeMortgageComponent', () => {
  let component: JusticeMortgageComponent;
  let fixture: ComponentFixture<JusticeMortgageComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ JusticeMortgageComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(JusticeMortgageComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should be created', () => {
    expect(component).toBeTruthy();
  });
});
