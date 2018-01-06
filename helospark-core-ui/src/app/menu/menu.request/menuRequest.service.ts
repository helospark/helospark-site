import { MenuItem } from './menuItem';
import { environment } from './../../../environments/environment';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class MenuRequestService {

  constructor(private http:HttpClient) { }

  getMenuItems():Observable<MenuItem[]> {
    return this.http.get<MenuItem[]>(environment.coreApiBaseUrl + '/categories');
  }
}
