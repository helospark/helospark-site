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
export class ArticleCommentComponent implements OnInit {
  comment:ArticleCommentForm;
  comments:Array<ArticleComment> = [];

  asd:string="";

  @Input("articleId")
  articleId:string;

  constructor(private authStore:AuthenticationStoreService, private commentService:ArticleCommentService) { }

  ngOnInit() {
    this.initNewComment();
    this.commentService.getComments(this.articleId, 0)
            .subscribe(comments => this.addToComments(comments));
  }

  private addToComments(comments:Array<ArticleComment>) {
    this.comments = this.comments.concat(comments); // TODO: remove duplicates
  }

  onCommentSubmit() {
    this.commentService.sendComment(this.comment)
          .subscribe(result => console.log("Comment sent"),
                     error => console.log("Error"));
    this.initNewComment();
  }

  initNewComment() {
    this.comment = new ArticleCommentForm();
    this.comment.articleId = this.articleId;
  }

  onVote(commentId:number, vote:number) {
    this.commentService.voteForComment(commentId, vote)
            .subscribe(result => console.log("Vote successful"),
                       error => console.log(error));
  }

}
