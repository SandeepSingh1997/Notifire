package com.example.notifire;

import java.util.Date;

public class Notice {
    private String title, subject, description;
    private Date publishDate;

    public Notice(){}

    public Notice(String title, String subject, String description, Date publishDate) {
        this.title = title;
        this.subject = subject;
        this.description = description;
        this.publishDate = publishDate;
    }

    public String getDescription() {
        return description;
    }

    public String getSubject() {
        return subject;
    }

    public String getTitle() {
        return title;
    }

    public Date getPublishDate() {
        return publishDate;
    }
}
