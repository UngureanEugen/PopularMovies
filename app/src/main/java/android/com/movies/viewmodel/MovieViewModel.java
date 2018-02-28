package android.com.movies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.com.movies.App;
import android.com.movies.data.Resource;
import android.com.movies.model.MovieEntity;
import android.com.movies.model.Review;
import android.com.movies.model.Video;
import android.com.movies.repository.MovieRepository;
import android.com.movies.ui.movie.SortType;
import java.util.List;
import javax.inject.Inject;

public class MovieViewModel extends AndroidViewModel {

  @Inject
  MovieRepository repository;

  private final MutableLiveData<Integer> sortType = new MutableLiveData<>();
  private final MutableLiveData<Integer> movieId = new MutableLiveData<>();

  private final LiveData<Resource<List<MovieEntity>>> movies =
      Transformations.switchMap(sortType,
          sort -> repository.getMovies(sort));

  private final LiveData<Resource<List<Video>>> videos =
      Transformations.switchMap(movieId, movieId -> repository.getVideos(movieId));
  private final LiveData<Resource<List<Review>>> reviews =
      Transformations.switchMap(movieId, movieId -> repository.getReviews(movieId));

  @Inject public MovieViewModel(Application application) {
    super(application);
    App.moviesComponent.inject(this);
  }

  public LiveData<Resource<List<MovieEntity>>> getMovies() {
    return movies;
  }

  public LiveData<Resource<List<Video>>> getVideos() {
    return videos;
  }

  public LiveData<Resource<List<Review>>> getReviews() {
    return reviews;
  }

  public void setSortType(@SortType int type) {
    sortType.setValue(type);
  }

  public void setMovieId(@SortType int type) {
    movieId.setValue(type);
  }
}