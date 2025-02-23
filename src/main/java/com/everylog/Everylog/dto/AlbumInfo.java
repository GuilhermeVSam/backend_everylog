package com.everylog.Everylog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AlbumInfo {
    @JsonProperty("id")
    private int id;

    @JsonProperty("title")
    private String title;

    @JsonProperty("link")
    private String link;

    @JsonProperty("cover")
    private String cover;

    @JsonProperty(value = "release_date", required = false)
    private String releaseDate;

    @JsonProperty("cover_small")
    private String coverSmall;

    @JsonProperty("cover_medium")
    private String coverMedium;

    @JsonProperty("cover_big")
    private String coverBig;

    @JsonProperty("cover_xl")
    private String coverXl;

    @JsonProperty("md5_image")
    private String md5Image;

    @JsonProperty("genre_id")
    private int genreId;

    @JsonProperty("nb_tracks")
    private int nbTracks;

    @JsonProperty("record_type")
    private String recordType;

    @JsonProperty("artist")
    private ArtistInfo artist;

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getCoverSmall() {
        return coverSmall;
    }

    public void setCoverSmall(String coverSmall) {
        this.coverSmall = coverSmall;
    }

    public String getCoverMedium() {
        return coverMedium;
    }

    public void setCoverMedium(String coverMedium) {
        this.coverMedium = coverMedium;
    }

    public String getCoverBig() {
        return coverBig;
    }

    public void setCoverBig(String coverBig) {
        this.coverBig = coverBig;
    }

    public String getCoverXl() {
        return coverXl;
    }

    public void setCoverXl(String coverXl) {
        this.coverXl = coverXl;
    }

    public String getMd5Image() {
        return md5Image;
    }

    public void setMd5Image(String md5Image) {
        this.md5Image = md5Image;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public int getNbTracks() {
        return nbTracks;
    }

    public void setNbTracks(int nbTracks) {
        this.nbTracks = nbTracks;
    }

    public String getRecordType() {
        return recordType;
    }

    public void setRecordType(String recordType) {
        this.recordType = recordType;
    }

    public ArtistInfo getArtist() {
        return artist;
    }

    public void setArtist(ArtistInfo artist) {
        this.artist = artist;
    }
}