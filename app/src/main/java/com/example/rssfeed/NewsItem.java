package com.example.rssfeed;

import java.io.Serializable;
import java.util.Date;

public class NewsItem implements Serializable
{
    private String title;
    private String link;
    private String description;
    private Date date;

    public NewsItem()
    {

    }

    public NewsItem(String title, String link, String description, Date date) {
        this.title = title;
        this.link = link;
        this.description = description;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean hasAllProperties()
    {
        return this.title != null && this.description != null
                && this.link != null && this.date != null;
    }

    @Override
    public String toString()
    {
        return this.title;
    }
}
