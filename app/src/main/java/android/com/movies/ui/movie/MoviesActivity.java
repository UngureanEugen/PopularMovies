package android.com.movies.ui.movie;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.com.movies.R;
import android.com.movies.databinding.ActivityMoviesBinding;
import android.com.movies.model.MovieEntity;
import android.com.movies.ui.movie.detail.MovieDetailsActivity;
import android.com.movies.viewmodel.MovieViewModel;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import static android.arch.lifecycle.Lifecycle.State.STARTED;
import static android.com.movies.ui.movie.SortType.MOST_POPULAR;
import static android.com.movies.ui.movie.SortType.TOP_RATED;

public class MoviesActivity extends AppCompatActivity
    implements LifecycleRegistryOwner, MovieClickCallback {

  private static final String SORT_KEY = "currentSort";
  public static final String EXTRA_MOVIE_IMAGE_TRANSITION = "movieImageTransition";
  public static final String EXTRA_MOVIE = "movie";

  private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
  private ActivityMoviesBinding binding;
  private MovieViewModel movieViewModel;
  private MoviesAdapter moviesAdapter;
  private SharedPreferences sharedPreferences;
  private @SortType int currentSortType;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
    moviesAdapter = new MoviesAdapter(this);
    binding.moviesList.setAdapter(moviesAdapter);
    binding.moviesList.setHasFixedSize(true);

    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      binding.moviesList.setLayoutManager(new GridLayoutManager(this, 2));
    } else {
      binding.moviesList.setLayoutManager(new GridLayoutManager(this, 3));
    }
    movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

    sharedPreferences = getSharedPreferences("movie-prefs", MODE_PRIVATE);
    currentSortType = sharedPreferences.getInt(SORT_KEY, TOP_RATED);

    subscribeUi();
    setSortType(currentSortType);
  }

  @Override
  public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
  }

  @Override
  public void onClick(MovieEntity movie, ImageView sharedView) {
    if (getLifecycle().getCurrentState().isAtLeast(STARTED)) {
      Intent intent = new Intent(this, MovieDetailsActivity.class);
      intent.putExtra(EXTRA_MOVIE, movie);
      intent.putExtra(EXTRA_MOVIE_IMAGE_TRANSITION, ViewCompat.getTransitionName(sharedView));
      ActivityOptionsCompat options = ActivityOptionsCompat
          .makeSceneTransitionAnimation(this, sharedView, ViewCompat.getTransitionName(sharedView));
      startActivity(intent, options.toBundle());
    }
  }

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case R.id.actionSortTopRated:
        setSortType(TOP_RATED);
        return true;
      case R.id.actionSortMostPopular:
        setSortType(MOST_POPULAR);
        return true;
      case android.R.id.home:
        supportFinishAfterTransition();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override protected void onPause() {
    super.onPause();
    SharedPreferences.Editor editor = sharedPreferences.edit();
    editor.putInt(SORT_KEY, currentSortType);
    editor.apply();
  }

  private void setSortType(@SortType int type) {
    binding.moviesList.scrollToPosition(0);
    movieViewModel.setSortType(type);
    currentSortType = type;
  }

  private void subscribeUi() {
    movieViewModel.getMovies().observe(this, resource -> {
      if (resource != null && resource.data != null) {
        binding.setIsLoading(false);
        moviesAdapter.setMovies(resource.data);
      } else {
        binding.setIsLoading(true);
      }
    });
  }
}
