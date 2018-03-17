import { ArticleCommentUser } from './article-comment-user';

export class ArticleComment {
    id:number;
    text:string;
    commentTime:string;
    votes:number;
    commenter:ArticleCommentUser;
    myVote:number;
    childComments:number;
    childrenLoaded:boolean = false;
}