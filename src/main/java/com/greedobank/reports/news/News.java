package com.greedobank.reports.news;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nonapi.io.github.classgraph.json.Id;

import java.time.LocalDateTime;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class News {
    private static int counter = 1;

    @Id
    private long id = counter++;
    private boolean displayOnSite;
    private boolean sendByEmail;
    private Content content;
    private boolean active;
    private String publicationDate;
    private String createdAt = LocalDateTime.now().withNano(0).toString();
    private String updatedAt = this.createdAt;

    @Override
    public String toString() {
        return "{\n" +
                "id: " + id +
                "displayOnSite: " + displayOnSite + "\n," +
                "sendByEmail: " + sendByEmail + "\n," +
                content + "\n" +
                "active: " + active + "\n," +
                "publicationDate: " + publicationDate + "\n," +
                "createdAt: " + createdAt + "\n," +
                "updatedAt: " + updatedAt + "\n," +
                "}";
    }
}
