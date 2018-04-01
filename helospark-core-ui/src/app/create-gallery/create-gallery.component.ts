import { CreateGalleryRequest } from './domain/create-gallery-request';
import { Component, OnInit } from '@angular/core';
import { RestCommunicatorService } from '../common/rest/rest-communicator.service';

@Component({
  selector: 'app-create-gallery',
  templateUrl: './create-gallery.component.html',
  styleUrls: ['./create-gallery.component.css']
})
export class CreateGalleryComponent implements OnInit {
  private request:CreateGalleryRequest = new CreateGalleryRequest();

  constructor(private restService:RestCommunicatorService) { }

  ngOnInit() {
  }

  createGallery() {
    this.restService.httpPost("/gallery", this.request)
      .subscribe(data => {
        console.log(data);
        this.request =  new CreateGalleryRequest();
      }, error => console.log(error));
  }

}
