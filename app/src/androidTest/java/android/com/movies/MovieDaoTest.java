package android.com.movies;

import android.arch.persistence.room.Room;
import android.com.movies.data.local.AppDatabase;
import android.com.movies.model.MovieEntity;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import java.util.ArrayList;
import java.util.List;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class MovieDaoTest {
  private final List<MovieEntity> movies = new ArrayList<>(0);
  private AppDatabase database;

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);
    database = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(), AppDatabase.class)
        .build();
    movies.add(new MovieEntity("Movie 1"));
    movies.add(new MovieEntity("Movie 2"));
  }

  @After
  public void tearDown() throws Exception {
    database.close();
  }

  @Test
  public void insertMovies() throws Exception {
    database.movieDao().insertMovies(movies);
    List<MovieEntity> entities = database.movieDao().loadMovies();
    assertEquals(2, entities.size());
  }
}