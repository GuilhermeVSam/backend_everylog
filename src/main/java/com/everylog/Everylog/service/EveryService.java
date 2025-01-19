package com.everylog.Everylog.service;

import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

import com.everylog.Everylog.dto.MovieSearch;
import com.everylog.Everylog.dto.OMDbResponse;


public class EveryService {

    public List<MovieSearch> getContentByName(String name, String omdbKey) {
        RestTemplate restTemplate = new RestTemplate();

        String url = String.format("http://www.omdbapi.com/?apikey=%s&s=%s", omdbKey, name);

        OMDbResponse omdbResponse = restTemplate.getForObject(url, OMDbResponse.class);

        List<MovieSearch> movies = new ArrayList<>();

        if ("True".equals(omdbResponse.getResponse())) {
            for (MovieSearch movie : omdbResponse.getSearch()) {
                movies.add(movie);
            }
        } else {
            System.out.println("Error: " + omdbResponse.getError());
        }
        System.out.println("aaa");
        return movies;
    }
}
