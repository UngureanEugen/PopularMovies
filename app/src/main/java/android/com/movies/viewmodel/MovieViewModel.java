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
import java.util.List;
import javax.inject.Inject;

public class MovieViewModel extends AndroidViewModel {
  @Inject
  MovieRepository repository;

  private final MutableLiveData<SORT> sortType = new MutableLiveData<>();
  private final LiveData<Resource<List<MovieEntity>>> movies =
      Transformations.switchMap(sortType, sort -> repository.getMovies(sort));

  @Inject public MovieViewModel(Application application) {
    super(application);
    App.moviesComponent.inject(this);
  }

  public LiveData<Resource<List<MovieEntity>>> getMovies() {
    return movies;
  }

  public void setSortType(SORT type) {
    sortType.setValue(type);
  }

  public enum SORT {
    TOP_RATED("vote_average.desc"),
    MOST_POPULAR("popularity.desc");

    SORT(String type) {
      sortBy = type;
    }

    public final String sortBy;
  }
}
