package android.com.movies.ui.movie;

import android.annotation.SuppressLint;
import android.com.movies.R;
import android.com.movies.data.DatabaseContract;
import android.com.movies.databinding.ActivityMoviesBinding;
import android.com.movies.model.MovieEntity;
import android.com.movies.sync.SyncUtil;
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

import static android.com.movies.ui.movie.SortType.MOST_POPULAR;
import static android.com.movies.ui.movie.SortType.TOP_RATED;

public class MoviesActivity extends AppCompatActivity
    implements LoaderManager.LoaderCallbacks<Cursor>, MovieClickCallback {

  public static final int MOVIE_LOADER_ID = 0;

  public static final String EXTRA_MOVIE_IMAGE_TRANSITION = "movieImageTransition";
  public static final String EXTRA_MOVIE = "movie";
  private static final String SORT_KEY = "currentSort";

  private ActivityMoviesBinding binding;
  private MoviesAdapter moviesAdapter;
  private SharedPreferences sharedPreferences;
  private @SortType int currentSortType;
  private CursorLoader loader;

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
    SyncUtil.initMoviesSync(this, currentSortType);
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
    if (loader != null) {
      loader.setSortOrder(DatabaseContract.getSort(currentSortType));
      loader.forceLoad();
    }
    SyncUtil.startMoviesImmediateSync(this, type);
  }

  @SuppressLint("StaticFieldLeak") @Override
  public Loader<Cursor> onCreateLoader(int id, Bundle args) {
    loader = new CursorLoader(this, DatabaseContract.CONTENT_URI_MOVIES, null, null, null,
        DatabaseContract.getSort(currentSortType));
    return loader;
  }

  @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
    moviesAdapter.swapCursor(data);
  }

  @Override public void onLoaderReset(Loader loader) {
    moviesAdapter.swapCursor(null);
  }
}
