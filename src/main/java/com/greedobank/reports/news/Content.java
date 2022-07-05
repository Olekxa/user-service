package com.greedobank.reports.news;

public record Content(String title,
                      String description) {

    @Override
    public String toString() {
        return "content: {" + "\n" +
                "title: " + title + ",\n" +
                "description: " + description + ",\n" +
                "}";
    }
}
