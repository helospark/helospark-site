import { Component, OnInit } from '@angular/core';
import { ArticleListService } from './article-list-service/article-list.service';
import { ArticleListEntry } from './article-list-entry';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-article-list',
  templateUrl: './article-list.component.html',
  styleUrls: ['./article-list.component.css']
})
export class ArticleListComponent implements OnInit {
  articles:ArticleListEntry[]= [];
  categoryName:string = "";
  constructor(private articleListService:ArticleListService, private route:ActivatedRoute, router:Router) {
    console.log("constructor");
  }

  ngOnInit() {
    console.log("init");
    this.route.params.subscribe(val => {
      this.doStuff();
    });
  }

  doStuff() {
    console.log("stuff");
    this.categoryName = this.route.snapshot.params['categoryName'];
    this.articleListService.getArticles(this.categoryName, 1)
      .subscribe(data => {
        this.articles = data;
        //console.log(data);
      }, error => {
        console.log(error);
      }, () => {
        console.log("Finished");
      });
  }

}
