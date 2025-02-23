package com.everylog.Everylog.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public class MovieSearchResponse {
    @JsonProperty("Search")
    private List<MovieSearch> search;

    @JsonProperty("totalResults")
    private String totalResults;

    @JsonProperty("Response")
    private String response;

    // Getters and Setters
    public List<MovieSearch> getSearch() {
        return search;
    }

    public void setSearch(List<MovieSearch> search) {
        this.search = search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(String totalResults) {
        this.totalResults = totalResults;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}