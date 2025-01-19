package com.everylog.Everylog.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;

import com.everylog.Everylog.service.EveryService;
import com.everylog.Everylog.dto.MovieSearch;

@RestController
public class EveryController {
    EveryService everyService = new EveryService();
    private String omdbKey = System.getenv("OMDB_API_KEY");

    @GetMapping("/api/movies")
    public ResponseEntity<List<MovieSearch>> searchMovieByName(@RequestParam(value = "name", defaultValue = "Memento") String name) {
        List<MovieSearch> movies = everyService.getContentByName(name, omdbKey);

        return ResponseEntity.ok(movies);
    }
}