package com.greedobank.reports.news;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode
public class News {
    private boolean displayOnSite;
    private boolean sendByEmail;
    private Content content;
    private String publicationDate;
    private boolean active;

    public News(boolean displayOnSite, boolean sendByEmail, String title, String content, boolean active) {
        this.displayOnSite = displayOnSite;
        this.sendByEmail = sendByEmail;
        this.content = new Content(title, content);
        this.publicationDate = LocalDateTime.now().toString();
        this.active = active;
    }
}
