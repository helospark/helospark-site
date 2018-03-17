import { ArticleCommentForm } from './../domain/article-comment-form';
import { Observable } from 'rxjs/Observable';
import { RestCommunicatorService } from './../../common/rest/rest-communicator.service';
import { Injectable } from '@angular/core';
import { ArticleComment } from '../domain/article-comment';

@Injectable()
export class ArticleCommentService {

  constructor(private restService:RestCommunicatorService) { }

  getComments(articleId:string, page:number, parentCommentId:number):Observable<Array<ArticleComment>> {
    if (parentCommentId == -1) {
      return this.restService.httpGet("/comment?articleId=" + articleId + "&page=" + page)
    } else {
      return this.restService.httpGet("/comment/" + parentCommentId + "/replies")      
    }
  }

  sendComment(comment:ArticleCommentForm):Observable<any> {
    return this.restService.httpPost("/comment", comment)
  }

  voteForComment(commentId:number, vote:number) {
    return this.restService.httpPost("/comment/" + commentId + "/vote?number=" + vote, {});
  }

  getVotes(comments:Array<ArticleComment>) {
    let query:string = "";
    for (let i = 0; i < comments.length; ++i) {
      query += comments[i].id;
      if (i < comments.length - 1) {
        query += ",";
      }
    }
    return this.restService.httpGet("/comment/vote/me?comments=" + query);
  }
}
