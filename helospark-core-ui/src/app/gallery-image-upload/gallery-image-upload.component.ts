import { ActivatedRoute } from '@angular/router';
import { RestCommunicatorService } from './../common/rest/rest-communicator.service';
import { ImageUploadForm } from './domain/image-upload-form';
import { Component, OnInit, ViewChild, ElementRef, Input } from '@angular/core';

@Component({
  selector: 'app-gallery-image-upload',
  templateUrl: './gallery-image-upload.component.html',
  styleUrls: ['./gallery-image-upload.component.css']
})
export class GalleryImageUploadComponent implements OnInit {
  @ViewChild("imageUploadInput")
  imageReference:ElementRef;
  
  @Input("galleryId")
  galleryId:string;
  
  private request:ImageUploadForm = new ImageUploadForm();

  constructor(private route:ActivatedRoute, private restService:RestCommunicatorService) { }

  ngOnInit() {
    this.route.params.subscribe(val => {
      this.galleryId = this.route.snapshot.params['galleryId'];
    });
  }

  uploadImage() {
    const formData: FormData = new FormData();
    let files:FileList = this.imageReference.nativeElement.files;
    formData.append('file', files[0]);
    formData.append('title', this.request.title);
    formData.append('description', this.request.description);
    this.request = new ImageUploadForm();
    this.restService.httpPost("/gallery/" + this.galleryId + "/image", formData)
      .subscribe(data => console.log(data),
                 error => console.log(error));
  }

}
