import { AuthenticationStoreService } from './../authentication-store/authentication-store.service';
import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor
} from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

@Injectable()
export class AuthenticationInterceptor implements HttpInterceptor {
  constructor(public authenticationStoreService: AuthenticationStoreService) {}
  intercept(request: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    var token = this.authenticationStoreService.getToken();
    console.log("Authentication interceptor");
    if (token != null) {
        var headerValue = "Bearer " + token;
        request = request.clone({
            setHeaders: {
                Authorization: headerValue
            }
        });
    }
    return next.handle(request);
  }
}