import { Component, OnInit } from '@angular/core';
import { ArticleListService } from './article-list-service/article-list.service';
import { ArticleListEntry } from './article-list-entry';
import { ActivatedRoute, Router } from '@angular/router';
import { CategoryInformationService } from './category-information-service/category-information.service';
import { CategoryInformation } from './category-information-service/category-information';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent implements OnInit {
  articles:ArticleListEntry[]= [];
  categoryInformation:CategoryInformation = new CategoryInformation();
  categoryName:string = "";
  currentPage:number = 1;

  constructor(private articleListService:ArticleListService, private route:ActivatedRoute, router:Router, private categoryInformationService:CategoryInformationService) {
  }

  ngOnInit() {
    this.route.params.subscribe(val => {
      this.categoryName = this.route.snapshot.params['categoryName'];
      this.displayList();
      this.updateCategoryInformation();
    });
  }

  displayList() {
    console.log("stuff");
    this.articleListService.getArticles(this.categoryName, this.currentPage)
      .subscribe(data => {
        this.articles = data;
        //console.log(data);
      }, error => {
        console.log(error);
      });
  }

  updateCategoryInformation() {
    this.categoryInformationService.getCategoryInformation(this.categoryName)
      .subscribe(data => {
        this.categoryInformation = data;
      }, error => {
        console.log(error);
      });
  }

  // Why are you doing this to me Angular??? 
  createRange(number){
    var items: number[] = [];
    for(var i = 1; i <= number; i++){
       items.push(i);
    }
    return items;
  }

}
