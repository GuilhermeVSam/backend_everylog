package com.everylog.Everylog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;

import com.everylog.Everylog.service.EveryService;
import com.everylog.Everylog.dto.MovieSearch;
import com.everylog.Everylog.dto.MusicBrainResponse;

@RestController
public class EveryController {
    EveryService everyService = new EveryService();
    private String omdbKey = System.getenv("OMDB_API_KEY");

    @GetMapping("/api/movies")
    public ResponseEntity<List<MovieSearch>> searchMovieByName(
            @RequestParam(value = "title", defaultValue = "Memento") String title) {
        try {
            List<MovieSearch> movies = everyService.getContentByName(title, omdbKey);
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/api/albums")
    public ResponseEntity<Map<String, MusicBrainResponse.Release>> searchAlbumsByName(
            @RequestParam(value = "title", defaultValue = "Violator") String title,
            @RequestParam(value = "artist", defaultValue = "Depeche_Mode") String artist) {
        try {
            Map<String, MusicBrainResponse.Release> albums = everyService.getAlbumsByName(title, artist);
            return ResponseEntity.ok(albums);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

}