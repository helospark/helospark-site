import { AuthenticationStoreService } from './../common/authentication-store/authentication-store.service';
import { LoginService } from './../common/login/login.service';
import { LoginData } from './../common/login-dialog/login-data';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  private loginData:LoginData;

  constructor(private loginService:LoginService, private authStore:AuthenticationStoreService) { }

  ngOnInit() {
    this.loginData = new LoginData();
  }

  onSubmit() {
    this.loginService.doLogin(this.loginData)
            .subscribe(tokens => this.authStore.setTokens(tokens),
                       error => console.log(error));
  }

}
