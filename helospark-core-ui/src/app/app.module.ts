import { MenuRequestService } from './menu/menu.request/menuRequest.service';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { Router, RouterModule, Routes } from '@angular/router';

import { AppComponent } from './app.component';
import { MenuComponent } from './menu/menu.component';
import { HeaderComponent } from './header/header.component';
import { FooterComponent } from './footer/footer.component';
import { HomeComponent } from './home/home.component';
import { HttpClientModule } from '@angular/common/http';
import { ArticleListComponent } from './article-list/article-list.component';

import { ArticleListService } from './article-list/article-list-service/article-list.service';
import { CategoryInformationService } from './article-list/category-information-service/category-information.service';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'category/:categoryName', component: ArticleListComponent },
 ];

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    HeaderComponent,
    FooterComponent,
    HomeComponent,
    ArticleListComponent
  ],
  imports: [
    BrowserModule,
    RouterModule.forRoot(routes),
    HttpClientModule
  ],
  providers: [
    MenuRequestService,
    ArticleListService,
    CategoryInformationService
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
