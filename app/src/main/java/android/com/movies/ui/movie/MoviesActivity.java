package android.com.movies.ui.movie;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.com.movies.R;
import android.com.movies.databinding.ActivityMoviesBinding;
import android.com.movies.model.Movie;
import android.com.movies.viewmodel.MovieViewModel;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static android.arch.lifecycle.Lifecycle.State.STARTED;

public class MoviesActivity extends AppCompatActivity implements LifecycleRegistryOwner, MovieClickCallback {

    private ActivityMoviesBinding binding;
    private MoviesAdapter moviesAdapter;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
        moviesAdapter = new MoviesAdapter(this);
        binding.moviesList.setAdapter(moviesAdapter);
        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
        subscribeUi(movieViewModel);
    }

    @Override
    public LifecycleRegistry getLifecycle() {
        return lifecycleRegistry;
    }

    @Override
    public void onClick(Movie movie) {
        if (getLifecycle().getCurrentState().isAtLeast(STARTED)) {
            // TODO: 7/5/17 start detailsActivity
        }
    }

    private void subscribeUi(MovieViewModel viewmodel) {
        viewmodel.getPopularMovies().observe(this, resource -> {
            if (resource != null && resource.data != null) {
                binding.setIsLoading(false);
                moviesAdapter.setMovies(resource.data);
            } else {
                binding.setIsLoading(true);
            }
        });
    }
}
