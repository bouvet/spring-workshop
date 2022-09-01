package com.endre.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/movies")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @GetMapping
    public List<MovieDto> getMovie(@RequestParam(required = false) String title){
        if (title == null) {
            return movieService.getMovies();
        }
        return movieService.getMovieByTitle(title);
    }

    @PostMapping
    public Integer createMovie(@RequestBody MovieDto movie){
        return movieService.createMovie(movie);
    }
}
