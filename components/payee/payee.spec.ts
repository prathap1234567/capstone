import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Payee } from './payee';

describe('Payee', () => {
  let component: Payee;
  let fixture: ComponentFixture<Payee>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Payee],
    }).compileComponents();

    fixture = TestBed.createComponent(Payee);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
