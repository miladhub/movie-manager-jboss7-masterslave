package movie.j2ee.ejb.session;

import javax.annotation.PostConstruct;
import javax.ejb.Stateless;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import movie.j2ee.dao.IMovieDAO;
import movie.j2ee.dao.MovieDAO;
import movie.j2ee.ejb.entity.Movie;
import movie.j2ee.interfaces.LocalMovieManager;
import movie.j2ee.interfaces.RemoteMovieManager;

@Stateless
public class MovieManagerSessionBean implements LocalMovieManager, RemoteMovieManager {
	@PersistenceContext
	private EntityManager em;
	private IMovieDAO movieDao;
	
	@Inject
    private Event<MovieEvent> movieEvent;
	
	@PostConstruct
	public void init() {
		movieDao = new MovieDAO(em);
	}

	@Override
	public Movie findByTitle(String title) {
		movieEvent.fire(new MovieEvent(title));
		return movieDao.findByTitle(title);
	}
}
