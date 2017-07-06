package android.com.movies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.com.movies.model.Movie;

import java.util.List;

public class MovieViewModel extends ViewModel {

    private static final MutableLiveData ABSENT = new MutableLiveData();

    {
        ABSENT.setValue(null);
    }

    private final LiveData<List<Movie>> observableMovies;

    public MovieViewModel(Application application) {
        super(application);
        observableMovies = Transformations.switchMap()
    }


    public LiveData getMovies() {
        return movies;
    }
}
