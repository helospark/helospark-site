import { ArticleDetail } from './article-detail';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from '../../../environments/environment';

@Injectable()
export class ArticleDetailService {
  constructor(private httpClient:HttpClient) { }

  getArticleDetail(categoryName:String, articleName:String) {
    let url = environment.coreApiBaseUrl + '/categories/' + categoryName + '/articles/' + articleName;
    return this.httpClient
      .get<ArticleDetail>(url);
  }
}
