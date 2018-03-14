import { AuthenticationStoreService } from './../common/authentication-store/authentication-store.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements OnInit {

  constructor(private authenticationStore:AuthenticationStoreService) { }

  ngOnInit() {
  }

}
