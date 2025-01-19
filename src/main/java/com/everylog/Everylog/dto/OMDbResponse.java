package com.everylog.Everylog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class OMDbResponse {
    @JsonProperty("Search")
    private List<MovieSearch> search;

    @JsonProperty("Response")
    private String response;

    @JsonProperty("Error")
    private String error;

    // Getters and setters
    public List<MovieSearch> getSearch() {
        return search;
    }

    public void setSearch(List<MovieSearch> search) {
        this.search = search;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}