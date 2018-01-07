import { TestBed, inject } from '@angular/core/testing';

import { CategoryInformationService } from './category-information.service';

describe('CategoryInformationService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [CategoryInformationService]
    });
  });

  it('should be created', inject([CategoryInformationService], (service: CategoryInformationService) => {
    expect(service).toBeTruthy();
  }));
});
