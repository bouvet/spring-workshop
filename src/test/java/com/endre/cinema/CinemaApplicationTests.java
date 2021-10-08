package com.endre.cinema;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest
class CinemaApplicationTests {

    @Autowired
    private MovieService movieService;

    @Test
    void testThatMovieHasBeenAddedToDatabase() {
        var numberOfMoviesInDb = movieService.getMovies().size();
        var movieDto = new MovieDto("Star Wars", 14);
        movieService.createMovie(movieDto);
        assertTrue(movieService.getMovies().size() > numberOfMoviesInDb);
    }

    @Test
    void shouldBeTestDataInDatabase(){
        assertFalse(movieService.getMovies().isEmpty());
    }
}
