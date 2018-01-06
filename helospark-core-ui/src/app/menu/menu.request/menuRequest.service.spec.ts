import { TestBed, inject } from '@angular/core/testing';

import { MenuRequestService } from './menuRequest.service';

describe('MenuRequestService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [MenuRequestService]
    });
  });

  it('should be created', inject([MenuRequestService], (service: MenuRequestService) => {
    expect(service).toBeTruthy();
  }));
});
