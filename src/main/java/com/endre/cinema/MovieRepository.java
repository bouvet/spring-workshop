package com.endre.cinema;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface MovieRepository extends CrudRepository<MovieEntity, Integer> {
    Collection<MovieEntity> findAllByTitle(String title);
}
