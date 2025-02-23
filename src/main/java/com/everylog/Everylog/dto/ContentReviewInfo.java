package com.everylog.Everylog.dto;

public class ContentReviewInfo {
    private String id;
    private String contentName;
    private String contentAuthor;
    private String contentYear;
    private String contentBanner;
    private String contentType;

    public String getContentName() {
        return contentName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public void setContentName(String contentName) {
        this.contentName = contentName;
    }

    public String getContentAuthor() {
        return contentAuthor;
    }

    public void setContentAuthor(String contentDirector) {
        this.contentAuthor = contentDirector;
    }

    public String getContentYear() {
        return contentYear;
    }

    public void setContentYear(String contentYear) {
        this.contentYear = contentYear;
    }

    public String getContentBanner() {
        return contentBanner;
    }

    public void setContentBanner(String contentBanner) {
        this.contentBanner = contentBanner;
    }

}
