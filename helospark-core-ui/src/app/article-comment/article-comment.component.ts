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
            .subscribe(comments => {
              this.addToComments(comments);
              this.commentService.getVotes(comments)
                .subscribe(result => this.fillMyVotes(result));
            });
  }

  private fillMyVotes(result:Array<ArticleCommentVote>) {
    for (let i = 0; i < result.length; ++i) {
      this.findComment(result[i].commentId)
                .myVote = result[i].direction;
    }
  }

  private findComment(commentId:number):ArticleComment {
    return this.comments
      .filter(a => a.id === commentId)[0];
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
    let comment = this.findComment(commentId);
    let changeInVote = vote - comment.myVote;
    comment.myVote = vote;
    comment.votes += changeInVote;
  }

}
