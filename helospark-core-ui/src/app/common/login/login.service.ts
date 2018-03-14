import { AuthenticationTokens } from './../authentication-store/authentication-tokens';
import { LoginData } from './../login-dialog/login-data';
import { environment } from './../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable()
export class LoginService {

  constructor(private http:HttpClient) { }

  doLogin(loginData:LoginData) {
    return this.http.post<AuthenticationTokens>(environment.coreApiBaseUrl + "/users/login", loginData)
  }
}
