import { ArticleCommentUser } from './article-comment-user';

export class ArticleComment {
    id:number;
    text:string;
    commentTime:string;
    votes:number;
    commenter:ArticleCommentUser;
}