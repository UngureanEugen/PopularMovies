package android.com.movies.ui.movie;

import android.annotation.SuppressLint;
import android.com.movies.R;
import android.com.movies.data.DatabaseContract;
import android.com.movies.data.remote.MovieService;
import android.com.movies.databinding.ActivityMoviesBinding;
import android.com.movies.model.MovieEntity;
import android.com.movies.repository.MovieRepository;
import android.com.movies.ui.movie.detail.MovieDetailsActivity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import javax.inject.Inject;

import static android.com.movies.ui.movie.SortType.MOST_POPULAR;
import static android.com.movies.ui.movie.SortType.TOP_RATED;

public class MoviesActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor>, MovieClickCallback {

  public static final int MOVIE_LOADER_ID = 0;

  @Inject
  MovieRepository repository;
  @Inject
  MovieService service;

  public static final String EXTRA_MOVIE_IMAGE_TRANSITION = "movieImageTransition";
  public static final String EXTRA_MOVIE = "movie";
  private static final String SORT_KEY = "currentSort";

  private ActivityMoviesBinding binding;
  private MoviesAdapter moviesAdapter;
  private SharedPreferences sharedPreferences;
  private @SortType int currentSortType;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = DataBindingUtil.setContentView(this, R.layout.activity_movies);
    moviesAdapter = new MoviesAdapter(null, this);
    binding.moviesList.setAdapter(moviesAdapter);
    binding.moviesList.setHasFixedSize(true);

    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
      binding.moviesList.setLayoutManager(new GridLayoutManager(this, 2));
    } else {
      binding.moviesList.setLayoutManager(new GridLayoutManager(this, 3));
    }

    sharedPreferences = getSharedPreferences("movie-prefs", MODE_PRIVATE);
    currentSortType = sharedPreferences.getInt(SORT_KEY, TOP_RATED);

    getSupportLoaderManager().initLoader(MOVIE_LOADER_ID, savedInstanceState, this);
    //subscribeUi();
    setSortType(currentSortType);
  }

  @Override
  public void onClick(MovieEntity movie, ImageView sharedView) {
    Intent intent = new Intent(this, MovieDetailsActivity.class);
    intent.putExtra(EXTRA_MOVIE, movie);
    intent.putExtra(EXTRA_MOVIE_IMAGE_TRANSITION, ViewCompat.getTransitionName(sharedView));
    ActivityOptionsCompat options = ActivityOptionsCompat
        .makeSceneTransitionAnimation(this, sharedView, ViewCompat.getTransitionName(sharedView));
    startActivity(intent, options.toBundle());
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
    currentSortType = type;
  }

  //private void subscribeUi() {
  //  movieViewModel.getMovies().observe(this, resource -> {
  //    if (resource != null && resource.data != null) {
  //      binding.setIsLoading(false);
  //      moviesAdapter.setMovies(resource.data);
  //    } else {
  //      binding.setIsLoading(true);
  //    }
  //  });
  //}

  @SuppressLint("StaticFieldLeak") @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    return new CursorLoader(this, DatabaseContract.CONTENT_URI, null, null, null, "");
  /*  String sortType = "";
    List<MovieEntity> movies = new ArrayList<>(0);
    return new AsyncTaskLoader(this) {

      @Override protected void onStartLoading() {
        if (!movies.isEmpty()) {
          deliverResult(movies);
        } else {
          binding.setIsLoading(true);
          forceLoad();
        }
      }

      @Override public List<MovieEntity> loadInBackground() {
        List<MovieEntity> responseMovies = new ArrayList<>(0);
        service.fetchMovies(sortType).enqueue(new Callback<ApiResponse<MoviesResponse>>() {
          @Override public void onResponse(Call<ApiResponse<MoviesResponse>> call,
              Response<ApiResponse<MoviesResponse>> response) {
            if (response.isSuccessful()
                && response.body() != null
                && response.body().body != null) {
              responseMovies.addAll(response.body().body.movies);
            } else {
              Toast.makeText(MoviesActivity.this, response.body().errorMessage, Toast.LENGTH_SHORT)
                  .show();
            }
          }

          @Override public void onFailure(Call<ApiResponse<MoviesResponse>> call, Throwable t) {
            binding.setIsLoading(false);
            Toast.makeText(MoviesActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
          }
        });
        return responseMovies;
      }
    };*/
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    moviesAdapter.swapCursor(data);
  }

  @Override public void onLoaderReset(Loader loader) {
    moviesAdapter.swapCursor(null);
  }
}
