package nett.formacion.aaa.module4.spring.movies.movies.repo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import nett.formacion.aaa.module4.spring.movies.movies.model.Movie;
  
public interface IMovieRepository extends CrudRepository<Movie, Long> {
 
    List<Movie> findByYearLessThan(int year);    
    List<Movie> findByYearGreaterThan(int year);
}
