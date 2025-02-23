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

    public List<MovieSearch> getContentByName(String name) {
        url = String.format("http://www.omdbapi.com/?apikey=%s&s=%s", omdbKey, name);

        MovieSearchResponse omdbResponse = restTemplate.getForObject(url, MovieSearchResponse.class);

        if ("True".equals(omdbResponse.getResponse())) {
            List<MovieSearch> movies = omdbResponse.getSearch();
            return movies;
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
        List<MovieSearch> movies = getContentByName(name);

        for (MovieSearch movie : movies) {
            ContentResponse cr = new ContentResponse();
            cr.setTitle(movie.getTitle());
            cr.setYear(movie.getYear());
            cr.setAuthor(movie.getDirector());
            cr.setCover(movie.getPoster());
            cr.setType(movie.getType());
            list.add(cr);
        }

        for (AlbumInfo album : albums) {
            ContentResponse cr = new ContentResponse();
            cr.setTitle(album.getTitle());
            cr.setCover(album.getCover());
            cr.setAuthor(album.getArtist().getName());
            cr.setType("album");
            list.add(cr);
        }

        return list;
    }

    public List<AlbumInfo> getAlbumByName(String name) {
        if (name != null) {
            String url = "https://api.deezer.com/search/album?q=" + name;
            DeezerAPIResponse response = restTemplate.getForObject(url, DeezerAPIResponse.class);
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
                contentReviewInfo.setContentName(ms.getTitle());
                contentReviewInfo.setContentDirector(ms.getDirector());
                contentReviewInfo.setContentYear(ms.getYear());
                contentReviewInfo.setContentBanner(ms.getPoster());
                break;

            case "album":
                AlbumInfo ai = getAlbumById(id);
                contentReviewInfo.setContentName(ai.getTitle());
                contentReviewInfo.setContentDirector(ai.getArtist().getName());
                contentReviewInfo.setContentYear(ai.getReleaseDate());
                contentReviewInfo.setContentBanner(ai.getCover());
                break;

            default:
                break;
        }

        return contentReviewInfo;
    }
}