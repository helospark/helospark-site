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
    var restCall:Observable<any> = this.http.get(this.buildUrl(url));
    return this.createResult(restCall);
  }

  httpPost(url:string, data:any) {
    var restCall:Observable<any> = this.http.post(this.buildUrl(url), data);
    return this.createResult(restCall);
  }

  private buildUrl(url:string):string {
    return environment.coreApiBaseUrl + url;
  }

  private createResult(restCall:Observable<any>):Observable<any> {
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

  private refreshToken():Observable<any> {
    var refreshToken = new RefreshToken(this.authenticationStore.getRefreshToken());
    return this.http.post<AuthenticationTokens>(environment.coreApiBaseUrl + "/users/login/refresh", refreshToken)
              .map(response => this.processResponse(response));
  }

  private relogin():Observable<any> {
    return this.loginDialog
      .show()
      .map(result => this.processResponse(result));
  }

  private processResponse(tokens:AuthenticationTokens) {
    this.authenticationStore.setTokens(tokens);
  }

}
