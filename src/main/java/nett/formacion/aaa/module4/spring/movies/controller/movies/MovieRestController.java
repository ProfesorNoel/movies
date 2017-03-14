package nett.formacion.aaa.module4.spring.movies.controller.movies;

import java.util.List;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import nett.formacion.aaa.module4.spring.movies.movies.model.Movie;
import nett.formacion.aaa.module4.spring.movies.movies.repo.IMovieRepository;

@RestController
public class MovieRestController {
	
	@Autowired
	private IMovieRepository repo;
	
	private static final Logger LOGGER = LogManager.getLogger(MovieRestController.class.getName());
	 
	public MovieRestController() {
		super();
	}
	
	// CREATE a NEW MOVIE
    @RequestMapping(path="/movies/create", produces = {"text/plain", "application/*"} )
    @ResponseBody
    public String createMovie(String title, int year) {
        
    	String response = null;
    	
    	Movie movie = new Movie(title, year);
        
        try {
            repo.save(movie);
            response = movie.toString() + " created successful";
        } catch (Exception e) {
        	LOGGER.error(e.getMessage());
        	response = e.getMessage();
        }
        
        return response;
    }
 
    // READ a EXISTING MOVIE FROM THE REPOSITORY
    @RequestMapping(path="/movies/get", produces = {"text/plain", "application/*"} )
    @ResponseBody
    public String readMovie(long id) {
        
    	String response = null;
    	
    	Movie movie = null;
    	
        try {
            movie = repo.findOne(id);
        } catch (Exception e) {
        	LOGGER.error(e.getMessage());
        	response = e.getMessage();
        }
        if (movie == null) {
        	response = "None Movie located in the data system with id:  " + id;
            LOGGER.error(response);
        } else {
        	response = movie.toString();
        }
        
        return response;
    }
 
    // UPDATE a EXISTING MOVIE FROM THE REPOSITORY
    @RequestMapping(path="/movies/update", produces = {"text/plain", "application/*"} )
    @ResponseBody
    public String updateMovie(long id, String title, int year) {
        
    	String response = null;
    	Movie movie;
        
    	try {
            movie = repo.findOne(id);
            
            if (movie != null)
            {
            	 movie.setTitle(title);
                 movie.setYear(year);
                 repo.save(movie);
                 
                 response = movie.toString();
            }
            else
            {
            	response = "None Movie located in the data system with id:  " + id;
            	LOGGER.error(response);
            }
        } catch (Exception e) {
        	LOGGER.error(e.getMessage());
            response = e.getMessage();
        }
        
    	return response;
    }
 
    // DELETE a MOVIE FROM THE REPOSITORY
    @RequestMapping(path="/movies/delete", produces = {"text/plain", "application/*"} )
    @ResponseBody
    public String deleteMovie(long id) {
        
    	String response = null;
    	Movie movie = null;
    	
    	try 
    	{    
    		movie = this.repo.findOne(id);
    		
    		if (movie != null)
    		{
    			repo.delete(movie);
    		}
        	
    	 } catch (Exception e) {
    		 response = e.getMessage();
    		 LOGGER.error(response);
         }	
       
        return movie.toString() + " successful deleted";
    }
    
    // Recover a List of MOVIES recorded before a given Date FROM THE REPOSITORY
    @RequestMapping(path="/movies/getAllBeforeYear",produces = {"text/plain", "application/*"} )
    public List<Movie> getMoviesBeforeYear(@RequestParam(value = "year") int year) {
        List<Movie> movies = repo.findByYearLessThan(year);
        return movies;
    }
    
    // Recover a List of MOVIES recorded before a given Date FROM THE REPOSITORY
    @RequestMapping(path="/movies/getAllAfterYear", produces = {"text/plain", "application/*"} )
    public List<Movie> getMoviesAfterYear(@RequestParam(value = "year") int year) {
        List<Movie> movies = repo.findByYearGreaterThan(year);
        return movies;
    }
}
