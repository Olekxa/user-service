package com.greedobank.reports.news;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Content {
    private String title;
    private String content;

    @Override
    public String toString() {
        return "content: {" +
                title + ",\n" +
                content + ",\n" +
                '}';
    }
}
