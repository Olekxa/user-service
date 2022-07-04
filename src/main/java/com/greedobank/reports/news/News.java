package com.greedobank.reports.news;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private boolean displayOnSite;
    private boolean sendByEmail;
    private Content content;
    private boolean active;
    private String publicationDate;
    private String createdAt;
    private String updatedAt;

    public News(boolean displayOnSite, boolean sendByEmail, Content content, String publicationDate, boolean active) {
        this.displayOnSite = displayOnSite;
        this.sendByEmail = sendByEmail;
        this.content = content;
        this.active = active;
        this.publicationDate = publicationDate;
        this.createdAt = LocalDateTime.now().toString();
        this.updatedAt = this.createdAt;
    }

    @Override
    public String toString() {
        return "{" +
                "displayOnSite=" + displayOnSite +
                ", sendByEmail=" + sendByEmail +
                ", content={" + content +
                ", active=" + active +
                ", publicationDate='" + publicationDate + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", updatedAt='" + updatedAt + '\'' +
                '}';
    }
}
