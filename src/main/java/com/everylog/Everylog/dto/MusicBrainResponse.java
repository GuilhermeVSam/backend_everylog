package com.everylog.Everylog.dto;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MusicBrainResponse {
    @JsonProperty("releases")
    private List<Release> releases;

    public List<Release> getReleases() {
        return releases;
    }

    public Map<String, Release> getUniqueReleasesByArtist() {
        return releases.stream()
                .collect(Collectors.toMap(
                        release -> release.getArtist().get(0).getArtist().getId(), // Group by artist ID
                        release -> release,                                       // Keep the release as value
                        (existing, replacement) -> existing                       // Keep the first release
                ));
    }

    public void setReleases(List<Release> releases) {
        this.releases = releases;
    }

    @Override
    public String toString() {
        return "MusicBrainResponse [releases=" + releases + "]";
    }

    // Nested DTO Classes
    public static class Release {
        @JsonProperty("id")
        private String id;

        @JsonProperty("title")
        private String title;

        @JsonProperty("artist-credit")
        private List<ArtistCredit> artistCredit;

        @JsonProperty("date")
        private String date;

        @JsonProperty("tags")
        private List<Tag> tags;

        // Getters and Setters
        public String getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public List<ArtistCredit> getArtist() {
            return artistCredit;
        }

        public String getDate() {
            return date;
        }

        public List<Tag> getTags() {
            return tags;
        }
    }

    public static class ArtistCredit {
        @JsonProperty("name")
        private String name;

        @JsonProperty("artist")
        private Artist artist;

        public Artist getArtist() {
            return artist;
        }

        public String getName() {
            return name;
        }

        public static class Artist {
            @JsonProperty("id")
            private String id;

            @JsonProperty("name")
            private String name;

            public String getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            @Override
            public String toString() {
                return "Artist [id=" + id + ", name=" + name + "]";
            }
        }
    }

    public static class Tag {
        @JsonProperty("name")
        private String name;

        @JsonProperty("count")
        private int count;

        public String getName() {
            return name;
        }

        public int getCount() {
            return count;
        }
    }
}
