import { environment } from './../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { ArticleListEntry } from '../article-list-entry';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class ArticleListService {

  constructor(private httpClient:HttpClient) { }

  getArticles(categoryName:string, page:number):Observable<ArticleListEntry[]> {
    let url = environment.coreApiBaseUrl + '/categories/' + categoryName + "/articles" + '?page=' + page;
    return this.httpClient.get<ArticleListEntry[]>(url);
  }
}
