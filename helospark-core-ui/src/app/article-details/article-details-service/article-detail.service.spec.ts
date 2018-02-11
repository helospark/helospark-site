import { TestBed, inject } from '@angular/core/testing';

import { ArticleDetailsService } from './article-details.service';

describe('ArticleDetailsService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ArticleDetailsService]
    });
  });

  it('should be created', inject([ArticleDetailsService], (service: ArticleDetailsService) => {
    expect(service).toBeTruthy();
  }));
});
