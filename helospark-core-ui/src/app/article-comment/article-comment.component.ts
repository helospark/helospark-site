import { ArticleCommentVote } from './domain/article-comment-vote';
import { ArticleCommentService } from './service/article-comment.service';
import { AuthenticationStoreService } from './../common/authentication-store/authentication-store.service';
import { Component, OnInit, Input } from '@angular/core';
import { ArticleCommentForm } from './domain/article-comment-form';
import { ArticleComment } from './domain/article-comment';

@Component({
  selector: 'app-article-comment',
  templateUrl: './article-comment.component.html',
  styleUrls: ['./article-comment.component.css']
})
export class ArticleCommentComponent {

  @Input("articleId")
  articleId:string;

  constructor(private authStore:AuthenticationStoreService) {}

}
