import { GalleryImage } from './gallery-image';

export class GalleryDetails {
    id:string;
    title:string;
    description:string;
    images:Array<GalleryImage>;
}