import { ArticleCommentService } from './../../service/article-comment.service';
import { AuthenticationStoreService } from './../../../common/authentication-store/authentication-store.service';
import { ArticleCommentVote } from './../../domain/article-comment-vote';
import { ArticleComment } from './../../domain/article-comment';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-comment-section',
  templateUrl: './comment-section.component.html',
  styleUrls: ['./comment-section.component.css']
})
export class CommentSectionComponent implements OnInit {
  comments:Array<ArticleComment> = [];

  @Input("articleId")
  articleId:string;

  @Input("parentCommentId")
  parentCommentId:number;

  @Input("level")
  levelValue:number;

  constructor(private authStore:AuthenticationStoreService, private commentService:ArticleCommentService) { }

  ngOnInit() {
    this.commentService.getComments(this.articleId, 0, this.parentCommentId)
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

  onVote(commentId:number, vote:number) {
    this.commentService.voteForComment(commentId, vote)
            .subscribe(result => console.log("Vote successful"),
                       error => console.log(error));
    let comment = this.findComment(commentId);
    let changeInVote = vote - comment.myVote;
    comment.myVote = vote;
    comment.votes += changeInVote;
  }

  loadChildComments(commentId:number) {
    let comment = this.findComment(commentId);
    comment.childrenLoaded = true;    
  }

  addNumbers(a:number, b:number):number {
    // Why???????
    return +a + +b;
  }

}
