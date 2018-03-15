package com.helospark.site.core.web.article.comment;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Component;

@Component
public class CommentPageableFactory {

    public Pageable createPageable(Integer page) {
        return PageRequest.of(page, 10, createSort());
    }

    private Sort createSort() {
        return new Sort(Direction.DESC, "commentTime");
    }
}
