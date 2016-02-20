package org.genericHpBuilder.Model;

import java.util.Date;

/**
 * Represents one version of a text file
 */
public class TextFileVersion {

    private Author author;
    private String content;
    private Date date;

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
