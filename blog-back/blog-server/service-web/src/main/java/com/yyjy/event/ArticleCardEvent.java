package com.yyjy.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class ArticleCardEvent extends ApplicationEvent {

    private final Long articleId;
    
    public ArticleCardEvent(Object source, Long articleId) {
        super(source);
        this.articleId = articleId;
    }
    // Getter省略
}