package android.com.movies;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.com.movies.repository.MovieRepository;
import android.com.movies.viewmodel.MovieViewModel;
import org.junit.Before;
import org.junit.Rule;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.InjectMocks;

@SuppressWarnings("unchecked")
@RunWith(JUnit4.class)
public class MovieViewModelTest {
  @Rule
  public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

  @InjectMocks MovieRepository movieRepository;

  @InjectMocks
  MovieViewModel movieViewModel;

  @Before
  public void setup() {
  }
}
