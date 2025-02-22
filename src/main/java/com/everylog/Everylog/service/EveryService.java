package com.everylog.Everylog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.everylog.Everylog.dto.MovieSearch;
import com.everylog.Everylog.dto.OMDbResponse;
import com.everylog.Everylog.dto.MusicBrainResponse;
import com.everylog.Everylog.dto.ContentResponse;

@Service
public class EveryService {
    RestTemplate restTemplate = new RestTemplate();
    String url;
    private String omdbKey = System.getenv("OMDB_KEY");

    public List<MovieSearch> getContentByName(String name) {
        url = String.format("http://www.omdbapi.com/?apikey=%s&s=%s", omdbKey, name);

        OMDbResponse omdbResponse = restTemplate.getForObject(url, OMDbResponse.class);

        if ("True".equals(omdbResponse.getResponse())) {
            List<MovieSearch> movies = omdbResponse.getSearch();
            for (MovieSearch movie : movies) {
                movie.setContentType("Movie/Series");
            }
            return movies;
        }

        return null;
    }

    public Map<String, MusicBrainResponse.Release> getAlbumsByName(String name, String artist) throws Exception {
        if (artist != null) {
            url = String.format("https://musicbrainz.org/ws/2/release?query=release:%s AND artist:%s&fmt=json", name,
                    artist);
        } else {
            url = String.format("https://musicbrainz.org/ws/2/release?query=release:%s&fmt=json", name);
        }

        MusicBrainResponse musicBrainResponse = restTemplate.getForObject(url, MusicBrainResponse.class);

        if (musicBrainResponse == null)
            throw new Exception("API Not Responding");

        Map<String, MusicBrainResponse.Release> releases = musicBrainResponse.getUniqueReleasesByArtist();
        for (MusicBrainResponse.Release release : releases.values()) {
            release.setContentType("Album");
        }

        return releases;
    }

    public ContentResponse getAllContent(String name, String artist) throws Exception {
        List<MovieSearch> movies = getContentByName(name);
        Map<String, MusicBrainResponse.Release> albums = getAlbumsByName(name, artist);

        ContentResponse contentResponse = new ContentResponse();
        contentResponse.setMovies(movies);
        contentResponse.setAlbums(new ArrayList<>(albums.values()));

        return contentResponse;
    }
}