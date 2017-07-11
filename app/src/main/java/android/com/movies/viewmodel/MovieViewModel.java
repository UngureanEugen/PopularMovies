package android.com.movies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.com.movies.App;
import android.com.movies.data.Resource;
import android.com.movies.data.remote.MovieEntity;
import android.com.movies.repository.MovieRepository;

import java.util.List;

import javax.inject.Inject;

public class MovieViewModel extends AndroidViewModel {
    @Inject
    MovieRepository repository;

    private LiveData<Resource<List<MovieEntity>>> observableMovies;

    @Inject
    public MovieViewModel(Application application) {
        super(application);
        App.moviesComponent.inject(this);
    }


    public LiveData<Resource<List<MovieEntity>>> getPopularMovies() {
        return repository.getPopularMovies();
    }
}
