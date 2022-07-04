package com.greedobank.reports.news;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Content {
    private String title;
    private String content;

    public Content(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
