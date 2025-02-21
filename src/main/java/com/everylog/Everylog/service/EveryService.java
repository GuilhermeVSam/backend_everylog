package com.everylog.Everylog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.List;
import java.util.Map;

import com.everylog.Everylog.dto.MovieSearch;
import com.everylog.Everylog.dto.OMDbResponse;
import com.everylog.Everylog.dto.MusicBrainResponse;

@Service
public class EveryService {
    RestTemplate restTemplate = new RestTemplate();
    String url;

    public List<MovieSearch> getContentByName(String name, String omdbKey) {
        url = String.format("http://www.omdbapi.com/?apikey=%s&s=%s", omdbKey, name);

        OMDbResponse omdbResponse = restTemplate.getForObject(url, OMDbResponse.class);

        if ("True".equals(omdbResponse.getResponse())) {
            return omdbResponse.getSearch();
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

        return musicBrainResponse.getUniqueReleasesByArtist();
    }
}
