import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllocatedAssetsComponent } from './allocated-assets';

describe('AllocatedAssets', () => {
  let component: AllocatedAssetsComponent;
  let fixture: ComponentFixture<AllocatedAssetsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AllocatedAssetsComponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AllocatedAssetsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
