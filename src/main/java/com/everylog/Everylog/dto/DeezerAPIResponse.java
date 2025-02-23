package com.everylog.Everylog.dto;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeezerAPIResponse {
    @JsonProperty("data")
    private List<AlbumInfo> data; // Renamed from "albums" to "data" for clarity

    public List<AlbumInfo> getData() {
        return data;
    }

    public void setData(List<AlbumInfo> data) {
        this.data = data;
    }
}