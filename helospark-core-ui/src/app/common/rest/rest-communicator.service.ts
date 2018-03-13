import { AuthenticationTokens } from './../authentication-store/authentication-tokens';
import { RefreshToken } from './refresh-token';
import { environment } from './../../../environments/environment';
import { LoginDialogService } from './../login-dialog/login-dialog-service';
import { AuthenticationStoreService } from './../authentication-store/authentication-store.service';
import { Observable } from 'rxjs/Observable';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

import 'rxjs/add/operator/mergeMap';

@Injectable()
export class RestCommunicatorService {

  constructor(private http:HttpClient, private loginDialog:LoginDialogService, private authenticationStore:AuthenticationStoreService) { }

  httpGet(url:string):Observable<any> {
    var restCall:Observable<any> = this.doRestCall(environment.coreApiBaseUrl + url);
    
    if (this.authenticationStore.isTokenRefreshRequired()) {
      return this.refreshToken()
        .mergeMap(ignored => restCall);
    } else if (this.authenticationStore.isReloginRequired()) {
      return this.relogin()
        .mergeMap(ignored => restCall); 
    /** tmp */
    } else if (this.authenticationStore.isLoggedOut()) {
      return this.relogin()
        .mergeMap(ignored => restCall);
    /** tmp end */
    } else {
      return restCall;
    }
  }

  doRestCall(url:string):Observable<Object> {
    return this.http.get(url);
  }

  refreshToken():Observable<any> {
    var refreshToken = new RefreshToken(this.authenticationStore.getRefreshToken());
    return this.http.post<AuthenticationTokens>(environment.coreApiBaseUrl + "/users/login/refresh", refreshToken)
              .map(response => this.processResponse(response));
  }


  relogin():Observable<any> {
    return this.loginDialog
      .show()
      .map(result => this.processResponse(result));
  }

  processResponse(tokens:AuthenticationTokens) {
    this.authenticationStore.setTokens(tokens);
  }

}
