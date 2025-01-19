package com.everylog.Everylog.dto;

public class Anime extends OMDbContent {
    @Override
    public String toString() {
        return "Anime{" +
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