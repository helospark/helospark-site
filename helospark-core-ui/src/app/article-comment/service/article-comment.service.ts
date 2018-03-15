import { ArticleCommentForm } from './../domain/article-comment-form';
import { Observable } from 'rxjs/Observable';
import { RestCommunicatorService } from './../../common/rest/rest-communicator.service';
import { Injectable } from '@angular/core';
import { ArticleComment } from '../domain/article-comment';

@Injectable()
export class ArticleCommentService {

  constructor(private restService:RestCommunicatorService) { }

  getComments(articleId:string, page:number):Observable<Array<ArticleComment>> {
    return this.restService.httpGet("/comment?articleId=" + articleId + "&page=" + page)
  }

  sendComment(comment:ArticleCommentForm):Observable<any> {
    return this.restService.httpPost("/comment", comment)
  }

  voteForComment(commentId:number, vote:number) {
    return this.restService.httpPost("/comment/" + commentId + "/vote?number=" + vote, {});
  }
}
