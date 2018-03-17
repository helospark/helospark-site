import { ArticleCommentService } from './article-comment/service/article-comment.service';
import { LoginDialogComponent } from './common/login-dialog/login-dialog.component';
import { AuthenticationStoreService } from './common/authentication-store/authentication-store.service';
import { RestCommunicatorService } from './common/rest/rest-communicator.service';
import { AuthenticationInterceptor } from './common/authentication-interceptor/authentication-interceptor';
import { LoginDialogService } from './common/login-dialog/login-dialog-service';
import { MenuRequestService } from './menu/menu.request/menuRequest.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Router, RouterModule, Routes } from '@angular/router';
import { BsModalModule } from 'ng2-bs3-modal';
import { FormsModule } from '@angular/forms';


import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { ArticleListComponent } from './article-list/article-list.component';

import { ArticleListService } from './article-list/article-list-service/article-list.service';
import { CategoryInformationService } from './article-list/category-information-service/category-information.service';
import { ArticleDetailsComponent } from './article-details/article-details.component';
import { ArticleDetailService } from './article-details/article-details-service/article-detail.service';
import { LoginFormComponent } from './login-form/login-form.component';
import { LoginService } from './common/login/login.service';
import { ArticleCommentComponent } from './article-comment/article-comment.component';
import { PostCommentFormComponent } from './article-comment/form-component/post-comment-form/post-comment-form.component';
import { CommentSectionComponent } from './article-comment/comment-section/comment-section/comment-section.component';


const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'category/:categoryName', component: ArticleListComponent },
  { path: 'category/:categoryName/article/:articleName', component: ArticleDetailsComponent }
 ];

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    ArticleListComponent,
    ArticleDetailsComponent,
    LoginDialogComponent,
    LoginFormComponent,
    ArticleCommentComponent,
    PostCommentFormComponent,
    CommentSectionComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule,
    BsModalModule,
    FormsModule
  ],
  providers: [
    MenuRequestService,
    ArticleListService,
    CategoryInformationService,
    ArticleDetailService,
    LoginDialogService,
    AuthenticationStoreService,
    LoginService,
    ArticleCommentService,
    RestCommunicatorService,
    {
      provide: HTTP_INTERCEPTORS,
      useClass: AuthenticationInterceptor,
      multi: true
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
