package com.greedobank.reports.news;

import lombok.Getter;

@Getter
public class NewsTemplateRequestDTO {
    private final boolean displayOnSite;
    private final boolean sendByEmail;
    private final Content content;
    private final String publicationDate;
    private final boolean active;

    public NewsTemplateRequestDTO(boolean displayOnSite, boolean sendByEmail, String title, String description, String publicationDate, boolean active) {
        this.displayOnSite = displayOnSite;
        this.sendByEmail = sendByEmail;
        this.content = new Content(title, description);
        this.publicationDate = publicationDate;
        this.active = active;
    }
}
