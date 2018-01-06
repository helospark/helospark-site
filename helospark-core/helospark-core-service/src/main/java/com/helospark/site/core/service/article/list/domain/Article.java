package com.helospark.site.core.service.article.list.domain;

import java.time.ZonedDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;

import com.helospark.site.core.service.article.categories.domain.ArticleCategory;

@Entity(name = "article")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String urlId;
    private String title;
    @CreatedDate
    @Column(name = "creation_time")
    private ZonedDateTime creationTime;
    @Basic(fetch = FetchType.LAZY)
    @ManyToOne
    private ArticleCategory category;
    @OneToOne
    private ArticleDetail details;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlId() {
        return urlId;
    }

    public void setUrlId(String urlId) {
        this.urlId = urlId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(ArticleCategory category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public ArticleCategory getCategory() {
        return category;
    }

    public ZonedDateTime getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(ZonedDateTime creationTime) {
        this.creationTime = creationTime;
    }

    public ArticleDetail getDetails() {
        return details;
    }

    public void setDetails(ArticleDetail details) {
        this.details = details;
    }

}
