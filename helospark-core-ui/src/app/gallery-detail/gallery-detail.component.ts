import { GalleryImage } from './domain/gallery-image';
import { environment } from './../../environments/environment';
import { AuthenticationStoreService } from './../common/authentication-store/authentication-store.service';
import { ActivatedRoute } from '@angular/router';
import { RestCommunicatorService } from './../common/rest/rest-communicator.service';
import { GalleryDetails } from './domain/gallery-details';
import { Component, OnInit, AfterViewInit, ViewChildren, QueryList, ViewChild } from '@angular/core';
import { NgxGalleryOptions, NgxGalleryImage, NgxGalleryComponent, NgxGalleryOrder, NgxGalleryAnimation } from 'ngx-gallery';

declare var $: any;


@Component({
  selector: 'app-gallery-detail',
  templateUrl: './gallery-detail.component.html',
  styleUrls: ['./gallery-detail.component.css']
})
export class GalleryDetailComponent implements OnInit {
  private thumbnailWidth=200;
  private thumbnailNumberPerRow = 4;

  private imageDetails:GalleryDetails = new GalleryDetails();
  private galleryId:string;
  private imageServer:string;

  galleryOptions: NgxGalleryOptions[] = [
    { "imageDescription": true,
      "previewSwipe":true,
      "fullWidth":false,
      "image": false,
      "height": "400px",
      "width":"100%",
      "thumbnailsRows": 2,
      "previewFullscreen": true,
      "previewCloseOnEsc": true,
      "previewKeyboardNavigation":true, 
      "previewCloseOnClick":true,
      "thumbnailsOrder": NgxGalleryOrder.Row,
      "previewZoom": true,
      "thumbnailsColumns": this.thumbnailNumberPerRow,
      "imageAnimation": NgxGalleryAnimation.Slide
    }
  ];
  galleryImages: NgxGalleryImage[] = [];

  @ViewChild("gallery")
  private galleryComponent:NgxGalleryComponent;


  constructor(private route:ActivatedRoute, private restService:RestCommunicatorService, private authenticationStore:AuthenticationStoreService) { }

  ngOnInit() {
    this.imageServer = environment.coreApiBaseUrl;
    this.route.params.subscribe(val => {
      this.galleryId = this.route.snapshot.params['galleryId'];
      this.displayArticleDetails();
    });
  }

  displayArticleDetails() {
    this.restService.httpGet("/gallery/" + this.galleryId)
      .subscribe(data => {
        this.createGalleryImages(data);
        this.imageDetails = data;
      },
                 e => console.log(e),
                () => console.log("Gallery loaded"));
  }

  createGalleryImages(data:GalleryDetails) {
    for (let image of data.images) {
      let galleryImage:NgxGalleryImage = new NgxGalleryImage({
           description: image.title,
           small: this.imageServer + image.thumbnailUrl,
           medium: this.imageServer + image.largeUrl,
           big: this.imageServer + image.originalUrl
      });
      this.galleryImages.push(galleryImage);
    }
    this.galleryOptions[0].thumbnailsRows = Math.ceil(data.images.length / this.thumbnailNumberPerRow);
    this.galleryOptions[0].height = (this.galleryOptions[0].thumbnailsRows * this.thumbnailWidth) + 'px';

    this.galleryComponent.options = this.galleryOptions;
    this.galleryComponent.ngOnInit();
    this.galleryComponent.ngAfterViewInit();
    this.galleryComponent.ngDoCheck();
  }

}
