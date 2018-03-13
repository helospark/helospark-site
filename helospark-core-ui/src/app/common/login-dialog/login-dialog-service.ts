import { Observable } from 'rxjs/Observable';
import { LoginDialogComponent } from './login-dialog.component';
import { Injectable } from '@angular/core';
import { fromPromise } from 'rxjs/observable/fromPromise';
import { AuthenticationTokens } from '../authentication-store/authentication-tokens';


@Injectable()
export class LoginDialogService {
    private loginDialogComponent:LoginDialogComponent;

    setLoginDialog(loginDialogComponent:LoginDialogComponent) {
        this.loginDialogComponent = loginDialogComponent;
    }

    show():Observable<AuthenticationTokens> {
        return fromPromise(this.loginDialogComponent.show());
    }
}