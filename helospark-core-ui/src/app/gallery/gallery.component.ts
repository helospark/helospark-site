import { RestCommunicatorService } from './../common/rest/rest-communicator.service';
import { GalleryListResponse } from './domain/gallery-list';
import { AuthenticationStoreService } from './../common/authentication-store/authentication-store.service';
import { Component, OnInit } from '@angular/core';
import { environment } from '../../environments/environment';

@Component({
  selector: 'app-gallery',
  templateUrl: './gallery.component.html',
  styleUrls: ['./gallery.component.css']
})
export class GalleryComponent implements OnInit {
  galleryList:Array<GalleryListResponse> = [];
  private imageServer:string;

  constructor(private authenticationStore:AuthenticationStoreService, private restService:RestCommunicatorService) { }

  ngOnInit() {
    this.imageServer = environment.coreApiBaseUrl;
    this.restService.httpGet("/gallery")
      .subscribe(data => {
        this.galleryList = data
      }, error => console.log(error));
  }

}
