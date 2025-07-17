import { TestBed } from '@angular/core/testing';

import { Asset } from '../../classes/asset.model';

describe('Asset', () => {
  let service: Asset;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(Asset);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
