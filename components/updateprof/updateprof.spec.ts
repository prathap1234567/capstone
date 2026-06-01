import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Updateprof } from './updateprof';

describe('Updateprof', () => {
  let component: Updateprof;
  let fixture: ComponentFixture<Updateprof>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Updateprof],
    }).compileComponents();

    fixture = TestBed.createComponent(Updateprof);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
