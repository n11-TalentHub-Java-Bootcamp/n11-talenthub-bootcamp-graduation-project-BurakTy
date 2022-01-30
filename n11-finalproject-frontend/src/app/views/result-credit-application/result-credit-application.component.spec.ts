import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ResultCreditApplicationComponent } from './result-credit-application.component';

describe('ResultCreditApplicationComponent', () => {
  let component: ResultCreditApplicationComponent;
  let fixture: ComponentFixture<ResultCreditApplicationComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ResultCreditApplicationComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ResultCreditApplicationComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
