package com.everylog.Everylog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.Map;

import com.everylog.Everylog.service.EveryService;
import com.everylog.Everylog.dto.ContentResponse;
import com.everylog.Everylog.dto.MovieSearch;
import com.everylog.Everylog.dto.MusicBrainResponse;

@RestController
@RequestMapping("/api/content")
public class EveryController {
    @Autowired
    EveryService everyService = new EveryService();

    @GetMapping("/movies")
    public ResponseEntity<List<MovieSearch>> searchMovieByName(
            @RequestParam(value = "title") String title) {
        try {
            List<MovieSearch> movies = everyService.getContentByName(title);
            return ResponseEntity.ok(movies);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/albums")
    public ResponseEntity<Map<String, MusicBrainResponse.Release>> searchAlbumsByName(
            @RequestParam(value = "title") String title,
            @RequestParam(value = "artist", required = false) String artist) {
        try {
            Map<String, MusicBrainResponse.Release> albums = everyService.getAlbumsByName(title, artist);
            return ResponseEntity.ok(albums);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/all")
    public ResponseEntity<ContentResponse> getAllContent(@RequestParam String name,
            @RequestParam(required = false) String artist) {
        try {
            ContentResponse contentResponse = everyService.getAllContent(name, artist);
            return ResponseEntity.ok(contentResponse);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

}