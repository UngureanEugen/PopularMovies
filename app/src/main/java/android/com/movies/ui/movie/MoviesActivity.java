package android.com.movies.ui.movie;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.com.movies.R;
import android.com.movies.databinding.ActivityMoviesBinding;
import android.com.movies.model.Movie;
import android.com.movies.viewmodel.MovieViewModel;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import static android.arch.lifecycle.Lifecycle.State.STARTED;

public class MoviesActivity extends AppCompatActivity implements LifecycleRegistryOwner, MovieClickCallback {

    private MovieViewModel movieViewModel;
    private ActivityMoviesBinding binding;
    private MoviesAdapter moviesAdapter;
    private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
        binding.moviesList.setAdapter(new MoviesAdapter(this));
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);
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
        viewmodel.getMovies().observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (o != null) {
                    binding.setIsLoading(false);
                } else {
                    binding.setIsLoading(true);
                }
            }
        });
    }
}
