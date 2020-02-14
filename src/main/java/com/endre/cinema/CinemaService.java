package com.endre.cinema;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CinemaService {

    private List<MovieDto> movies = new ArrayList<>();

    public CinemaService(){
        movies.add(new MovieDto("Avengers Endgame", 13));
        movies.add(new MovieDto("Star Wars Episode IV", 10));
    }

    public List<MovieDto> getMovies(){
        return movies;
    }
}
