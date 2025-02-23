package com.everylog.Everylog.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.everylog.Everylog.dto.AlbumInfo;
import com.everylog.Everylog.dto.ContentResponse;
import com.everylog.Everylog.dto.ContentReviewInfo;
import com.everylog.Everylog.dto.DeezerAPIResponse;
import com.everylog.Everylog.dto.MovieSearch;
import com.everylog.Everylog.dto.MovieSearchResponse;

@Service
public class EveryService {
    RestTemplate restTemplate = new RestTemplate();
    String url;
    private String omdbKey = System.getenv("OMDB_KEY");

    public List<MovieSearch> getMoviesByName(String name) {
        if (name != null) {
            url = String.format("http://www.omdbapi.com/?apikey=%s&s=%s", omdbKey, name);

            MovieSearchResponse omdbResponse = restTemplate.getForObject(url, MovieSearchResponse.class);

            if (omdbResponse == null || omdbResponse.getSearch() == null) {
                return new ArrayList<>();
            }
            return omdbResponse.getSearch();
        }

        return null;
    }

    public MovieSearch getMovieById(String id) {
        url = String.format("http://www.omdbapi.com/?apikey=%s&i=%s", omdbKey, id);

        MovieSearch omdbResponse = restTemplate.getForObject(url, MovieSearch.class);

        return omdbResponse;
    }

    public List<ContentResponse> getAllContent(String name, String artist) throws Exception {
        List<ContentResponse> list = new ArrayList<>();
        List<AlbumInfo> albums = getAlbumByName(name);
        List<MovieSearch> movies = getMoviesByName(name);

        if (!movies.isEmpty()) {
            for (MovieSearch movie : movies) {
                ContentResponse cr = new ContentResponse();
                cr.setId(movie.getImdbID());
                cr.setTitle(movie.getTitle());
                cr.setYear(movie.getYear());
                cr.setCover(movie.getPoster());
                cr.setType(movie.getType());
                list.add(cr);
            }
        }

        if (!albums.isEmpty()) {
            for (AlbumInfo album : albums) {
                ContentResponse cr = new ContentResponse();
                cr.setId("" + album.getId());
                cr.setTitle(album.getTitle());
                cr.setCover(album.getCover());
                cr.setYear(album.getReleaseDate());
                cr.setType(album.getRecordType());
                list.add(cr);
            }
        }

        return list;
    }

    public List<AlbumInfo> getAlbumByName(String name) {
        if (name != null) {
            String url = "https://api.deezer.com/search/album?q=" + name;
            DeezerAPIResponse response = restTemplate.getForObject(url, DeezerAPIResponse.class);
            if (response == null || response.getData() == null) {
                return new ArrayList<>();
            }
            return response.getData();
        }
        return null;
    }

    public AlbumInfo getAlbumById(String id) {
        if (id != null) {
            url = String.format("https://api.deezer.com/album/%s", id);

            AlbumInfo albumInfo = restTemplate.getForObject(url, AlbumInfo.class);

            return albumInfo;
        }
        return null;
    }

    public ContentReviewInfo getContentInfo(String id, String type) {
        ContentReviewInfo contentReviewInfo = new ContentReviewInfo();
        switch (type) {
            case "movie":
                MovieSearch ms = getMovieById(id);
                contentReviewInfo.setId(ms.getImdbID());
                contentReviewInfo.setContentName(ms.getTitle());
                contentReviewInfo.setContentAuthor(ms.getDirector());
                contentReviewInfo.setContentYear(ms.getYear());
                contentReviewInfo.setContentBanner(ms.getPoster());
                contentReviewInfo.setContentType("movie");
                break;

            case "game":
                MovieSearch gm = getMovieById(id);
                contentReviewInfo.setId(gm.getImdbID());
                contentReviewInfo.setContentName(gm.getTitle());
                contentReviewInfo.setContentAuthor(gm.getDirector());
                contentReviewInfo.setContentYear(gm.getYear());
                contentReviewInfo.setContentBanner(gm.getPoster());
                contentReviewInfo.setContentType("movie");
                break;

            case "album":
                AlbumInfo ai = getAlbumById(id);
                contentReviewInfo.setId("" + ai.getId());
                contentReviewInfo.setContentName(ai.getTitle());
                contentReviewInfo.setContentAuthor(ai.getArtist().getName());
                contentReviewInfo.setContentYear(ai.getReleaseDate());
                contentReviewInfo.setContentBanner(ai.getCover());
                contentReviewInfo.setContentType("album");
                break;

            default:
                break;
        }

        return contentReviewInfo;
    }
}