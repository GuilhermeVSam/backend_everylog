package com.everylog.Everylog.dto;

import java.util.List;

public class ContentResponse {
    private List<MovieSearch> movies;
    private List<MusicBrainResponse.Release> albums;

    // Getters and setters
    public List<MovieSearch> getMovies() {
        return movies;
    }

    public void setMovies(List<MovieSearch> movies) {
        this.movies = movies;
    }

    public List<MusicBrainResponse.Release> getAlbums() {
        return albums;
    }

    public void setAlbums(List<MusicBrainResponse.Release> albums) {
        this.albums = albums;
    }
}