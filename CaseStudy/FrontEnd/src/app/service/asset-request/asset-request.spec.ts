import { TestBed } from '@angular/core/testing';

import { AssetRequest } from '../../classes/asset-request.model';
describe('AssetRequest', () => {
  let service: AssetRequest;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssetRequest);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
