package com.everylog.Everylog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Movie extends OMDbContent{
    @Override
    public String toString() {
        return "Movie{" +
                "title='" + getTitle() + '\'' +
                ", year='" + getYear() + '\'' +
                ", genre='" + getGenre() + '\'' +
                ", director='" + getDirector() + '\'' +
                ", released='" + getReleased() + '\'' +
                ", runtime='" + getRuntime() + '\'' +
                ", plot='" + getPlot() + '\'' +
                '}';
    }
}
