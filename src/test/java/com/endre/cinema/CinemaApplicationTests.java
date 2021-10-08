package com.endre.cinema;

import org.springframework.beans.factory.annotation.Autowired;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


@ExtendWith(SpringExtension.class)
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
