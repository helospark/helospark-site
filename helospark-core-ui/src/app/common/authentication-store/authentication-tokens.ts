import { Token } from './token';

export class AuthenticationTokens {
    authenticationToken:Token;
    refreshToken:Token;
    admin:boolean = false;
}