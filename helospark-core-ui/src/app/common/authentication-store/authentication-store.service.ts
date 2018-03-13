import { Token } from './token';
import { AuthenticationTokens } from './authentication-tokens';
import { Injectable, OnInit } from '@angular/core';

@Injectable()
export class AuthenticationStoreService implements OnInit {
  private thresholdTime:number = 1000;

  private tokenKey:string = "Authentication-tokens";
  private authenticationTokens:AuthenticationTokens;

  constructor() { }

  ngOnInit() {
    var result = localStorage.getItem(this.tokenKey);
    if (result) {
      this.authenticationTokens = JSON.parse(result);
    }
  }

  getToken():string {
    if (this.authenticationTokens && this.isTokenValid(this.authenticationTokens.authenticationToken)) {
      return this.authenticationTokens.authenticationToken.token;
    } else {
      return null;
    }
  }

  setTokens(tokens:AuthenticationTokens) {
    this.authenticationTokens = tokens;
  }

  getRefreshToken():string {
    if (this.authenticationTokens && this.isTokenValid(this.authenticationTokens.refreshToken)) {
      return this.authenticationTokens.refreshToken.token;
    } else {
      return null;
    }    
  }

  updateToken(tokens:AuthenticationTokens) {
    this.authenticationTokens = tokens;
    localStorage.setItem(this.tokenKey, JSON.stringify(this.authenticationTokens));
  }

  clearToken() {
    this.authenticationTokens = null;
    localStorage.removeItem(this.tokenKey);
  }

  isTokenRefreshRequired():boolean {
    return this.authenticationTokens != null
              &&  this.authenticationTokens.authenticationToken.expiresAt - Date.now() < this.thresholdTime
              && this.authenticationTokens.refreshToken.expiresAt - Date.now() > this.thresholdTime;
  }

  isReloginRequired():boolean {
    return this.authenticationTokens != null
              && this.authenticationTokens.refreshToken.expiresAt - Date.now() < this.thresholdTime;
  }

  isLoggedIn():boolean {
    return this.authenticationTokens != null && !this.isReloginRequired();
  }

  isLoggedOut():boolean {
    return !this.isLoggedIn();
  }

  private isTokenValid(token:Token):boolean {
    var millisecondsTillExpiry:number = token.expiresAt - Date.now(); 
    return millisecondsTillExpiry > this.thresholdTime;
  }

}
