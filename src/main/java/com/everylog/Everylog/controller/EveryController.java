package com.everylog.Everylog.controller;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import com.everylog.Everylog.service.EveryService;
import com.everylog.Everylog.dto.AlbumInfo;
import com.everylog.Everylog.dto.ContentResponse;
import com.everylog.Everylog.dto.ContentReviewInfo;
import com.everylog.Everylog.dto.MovieSearch;

@RestController
@RequestMapping("/api/content")
public class EveryController {
    @Autowired
    EveryService everyService = new EveryService();

    @GetMapping("/movies")
    public ResponseEntity<List<MovieSearch>> searchMovieByName(
            @RequestParam(value = "title") String title) {
        try {
            List<MovieSearch> movies = everyService.getMoviesByName(title);
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/movieId")
    public ResponseEntity<MovieSearch> searchMovieById(@RequestParam(value = "id") String id) {
        MovieSearch movie = everyService.getMovieById(id);

        return ResponseEntity.ok(movie);
    }

    @GetMapping("/albums")
    public ResponseEntity<List<AlbumInfo>> getAlbumByName(@RequestParam String name) {
        return ResponseEntity.ok(everyService.getAlbumByName(name));
    }

    @GetMapping("/albumId")
    public ResponseEntity<AlbumInfo> searchAlbumById(@RequestParam(value = "id") String id) {
        AlbumInfo album = everyService.getAlbumById(id);

        return ResponseEntity.ok(album);
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContentResponse>> getAllContent(@RequestParam String name,
            @RequestParam(required = false) String artist) {
        try {
            List<ContentResponse> contentResponse = everyService.getAllContent(name, artist);
            return ResponseEntity.ok(contentResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @GetMapping("/contentId")
    public ResponseEntity<ContentReviewInfo> getContentById(@RequestParam String id, @RequestParam String type) {
        try {
            ContentReviewInfo contentReviewInfo = everyService.getContentInfo(id, type);

            return ResponseEntity.ok(contentReviewInfo);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}