import { ArticleDetailService } from './article-details-service/article-detail.service';
import { ArticleDetail } from './article-details-service/article-detail';
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-article-details',
  templateUrl: './article-details.component.html',
  styleUrls: ['./article-details.component.css']
})
export class ArticleDetailsComponent implements OnInit {
  articleDetail:ArticleDetail;
  categoryName:String;
  articleName:String;
  constructor(private route:ActivatedRoute, private articleDetailsService:ArticleDetailService) { }

  ngOnInit() {
    this.route.params.subscribe(val => {
      this.categoryName = this.route.snapshot.params['categoryName'];
      this.articleName = this.route.snapshot.params['articleName'];
      this.displayArticleDetails();
    });
  }

  displayArticleDetails() {
    this.articleDetailsService.getArticleDetail(this.categoryName, this.articleName)
      .subscribe(a => this.articleDetail = a,
                 e => console.log(e),
                () => console.log("Articles loaded"));
  }

}
