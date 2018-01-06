import { MenuItem } from './menu.request/menuItem';
import { MenuRequestService } from './menu.request/menuRequest.service';
import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-menu',
  templateUrl: './menu.component.html',
  styleUrls: ['./menu.component.css']
})
export class MenuComponent implements OnInit {

  private menuItems:MenuItem[];
  
  constructor(private menuRequestService:MenuRequestService) { }

  ngOnInit() {
    this.menuRequestService.getMenuItems()
      .subscribe(menuItems => {
        console.log(menuItems);
        this.menuItems=menuItems
      });
  }

}
