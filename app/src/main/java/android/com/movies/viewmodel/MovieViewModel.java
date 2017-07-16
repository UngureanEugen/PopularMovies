package android.com.movies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.com.movies.App;
import android.com.movies.data.Resource;
import android.com.movies.model.MovieEntity;
import android.com.movies.repository.MovieRepository;
import android.com.movies.ui.movie.SortType;
import java.util.List;
import javax.inject.Inject;

public class MovieViewModel extends AndroidViewModel {

  @Inject
  MovieRepository repository;

  private final MutableLiveData<Integer> sortType = new MutableLiveData<>();
  private final LiveData<Resource<List<MovieEntity>>> movies =
      Transformations.switchMap(sortType, sort -> repository.getMovies(sort));

  @Inject MovieViewModel(Application application) {
    super(application);
    App.moviesComponent.inject(this);
  }

  public LiveData<Resource<List<MovieEntity>>> getMovies() {
    return movies;
  }

  public void setSortType(@SortType int type) {
    sortType.setValue(type);
  }
}
