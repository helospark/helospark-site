import { environment } from './../../../environments/environment';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { CategoryInformation } from './category-information';

@Injectable()
export class CategoryInformationService {

  constructor(private httpClient:HttpClient) { }

  getCategoryInformation(categoryName:String):Observable<CategoryInformation> {
      let url = environment.coreApiBaseUrl + '/categories/' + categoryName + "/information";
      return this.httpClient.get<CategoryInformation>(url);
    }
}
