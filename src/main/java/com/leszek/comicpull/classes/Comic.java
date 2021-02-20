package com.leszek.comicpull.classes;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

public class Comic implements Serializable {
    private String title;
    private String imgUrl;
    private String webUrl;
    private Date publicationDate;

    public Comic() {
        // default constructor for jackson
    }

    public Comic(String title, String imgUrl, String webUrl, Date publicationDate) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.webUrl = webUrl;
        this.publicationDate = publicationDate;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    //setting to make the publication date a string when converted to json
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    public Date getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Date publicationDate) {
        this.publicationDate = publicationDate;
    }
}
