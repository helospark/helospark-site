import { LoginService } from './../login/login.service';
import { environment } from './../../../environments/environment';
import { HttpClient } from '@angular/common/http';
import { LoginDialogService } from './login-dialog-service';
import { BsModalComponent } from 'ng2-bs3-modal';
import { Component, OnInit, ViewChild, AfterViewInit } from '@angular/core';
import { LoginData } from './login-data';
import { AuthenticationTokens } from '../authentication-store/authentication-tokens';

@Component({
  selector: 'app-login-dialog',
  templateUrl: './login-dialog.component.html',
  styleUrls: ['./login-dialog.component.css']
})
export class LoginDialogComponent implements OnInit, AfterViewInit {
  @ViewChild("modal")
  private modal:BsModalComponent;

  private loginData:LoginData = new LoginData();

  private resolve;

  constructor(private loginDialogService:LoginDialogService, private loginService:LoginService) { }

  ngOnInit() {
    console.log("Login dialog component const");
  }

  ngAfterViewInit() {
    this.loginDialogService.setLoginDialog(this);
  }

  show():Promise<any> {
    this.loginData = new LoginData();
    this.modal.open();
    return new Promise((resolve, reject) => {this.resolve=resolve});
  }

  onSubmit() {
    //console.log(this.loginData.userName + " " + this.loginData.password;
    this.loginService.doLogin(this.loginData)
          .subscribe(result => {
            this.resolve(result);
            this.modal.close();
          }, error => console.log("Auth error " + error)); 
  }

  onCancel() {
    this.modal.dismiss();
    this.resolve();
  }
}
