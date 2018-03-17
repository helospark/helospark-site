import { ArticleCommentService } from './../../service/article-comment.service';
import { ArticleCommentForm } from './../../domain/article-comment-form';
import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-post-comment-form',
  templateUrl: './post-comment-form.component.html',
  styleUrls: ['./post-comment-form.component.css']
})
export class PostCommentFormComponent implements OnInit {
  comment:ArticleCommentForm;
  @Input("articleId")
  articleId:string;
  @Input("parentCommentId")
  parentCommentId:string;
  @Input("visible")
  visible:boolean;

  constructor(private commentService:ArticleCommentService) { }

  ngOnInit() {
    this.initNewComment();
  }

  initNewComment() {
    this.comment = new ArticleCommentForm();
    this.comment.articleId = this.articleId;
    if (this.parentCommentId != "-1") {
      this.comment.parentCommentId= this.parentCommentId;
    }
  }

  onCommentSubmit() {
    this.commentService.sendComment(this.comment)
          .subscribe(result => console.log("Comment sent"),
                     error => console.log("Error"));
    this.initNewComment();
  }

  toggleVisible() {
    this.visible = !this.visible;
  }
}
