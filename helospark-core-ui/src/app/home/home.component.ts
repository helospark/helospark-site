import { RestCommunicatorService } from './../common/rest/rest-communicator.service';
import { Component, OnInit, ViewChild } from '@angular/core';
import { BsModalComponent } from 'ng2-bs3-modal';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {


  constructor(private asd:RestCommunicatorService) {

  }

  ngOnInit() {
  }

  clicked() {
    this.asd.httpGet("/categories")
      .subscribe(a => console.log(a));
  }

}
