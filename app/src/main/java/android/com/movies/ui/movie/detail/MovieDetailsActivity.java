package android.com.movies.ui.movie.detail;

import android.com.movies.R;
import android.com.movies.data.DatabaseContract;
import android.com.movies.databinding.ActivityDetailsBinding;
import android.com.movies.di.NetworkModule;
import android.com.movies.model.MovieEntity;
import android.com.movies.model.Video;
import android.com.movies.sync.SyncUtil;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MenuItem;
import com.squareup.picasso.Picasso;

import static android.com.movies.ui.movie.MoviesActivity.EXTRA_MOVIE;
import static android.com.movies.ui.movie.MoviesActivity.EXTRA_MOVIE_IMAGE_TRANSITION;
import static android.com.movies.util.Utils.dpToPixel;

public class MovieDetailsActivity extends AppCompatActivity
    implements TrailersAdapter.OnTrailerClickListener {

  public static final int TRAILERS_LOADER_ID = 1;
  public static final int REVIES_LOADER_ID = 2;

  private MovieEntity movie = null;
  private TrailersAdapter trailersAdapter;
  private ReviewsAdapter reviewsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityDetailsBinding binding =
        DataBindingUtil.setContentView(this, R.layout.activity_details);

    Bundle extras = getIntent().getExtras();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      String transition = extras.getString(EXTRA_MOVIE_IMAGE_TRANSITION);
      binding.ivPoster.setTransitionName(transition);
    }

    if (extras != null) {
      movie = extras.getParcelable(EXTRA_MOVIE);
      trailersAdapter = new TrailersAdapter(this);
      reviewsAdapter = new ReviewsAdapter();
      ViewCompat.setElevation(binding.ltTitle, dpToPixel(this, 6f));
      binding.tvTitle.setText(movie.title);
      binding.tvReleaseDate.setText(movie.releaseDate);
      binding.tvVote.setText(String.format("%s/10", movie.voteAverage));
      binding.tvPlotSynopsis.setText(movie.overview);

      binding.rvTrailers.setLayoutManager(new LinearLayoutManager(this));
      binding.rvReviews.setLayoutManager(new LinearLayoutManager(this));

      binding.rvTrailers.addItemDecoration(
          new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
      binding.rvReviews.addItemDecoration(
          new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

      binding.rvTrailers.setAdapter(trailersAdapter);
      binding.rvReviews.setAdapter(reviewsAdapter);

      binding.rvTrailers.setNestedScrollingEnabled(false);
      binding.rvReviews.setNestedScrollingEnabled(false);

      binding.rvTrailers.setHasFixedSize(true);
      binding.rvReviews.setHasFixedSize(true);

      Picasso.with(this)
          .load(NetworkModule.IMAGE_URL + movie.posterPath)
          .noFade()
          .into(binding.ivPoster);

      getSupportLoaderManager().initLoader(TRAILERS_LOADER_ID, savedInstanceState,
          trailersCallback);
      getSupportLoaderManager().initLoader(REVIES_LOADER_ID, savedInstanceState,
          reviewsCallback);

      SyncUtil.initTrailersSync(this, movie.id);
      SyncUtil.initReviewsSync(this, movie.id);
    } else {
      finish();
    }
  }

  private void subscribeUi() {
 /*   movieViewModel.getVideos().observe(this, trailers -> {
      if (trailers != null && trailers.data != null) {
        trailersAdapter.setTrailers(trailers.data);
      }
    });
    movieViewModel.getReviews().observe(this, reviews -> {
      if (reviews != null && reviews.data != null) {
        reviewsAdapter.setReviews(reviews.data);
      }
    });*/
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    switch (item.getItemId()) {
      case android.R.id.home:
        supportFinishAfterTransition();
        return true;
      default:
        return super.onOptionsItemSelected(item);
    }
  }

  @Override public void onClick(Video trailer) {
    final Uri youtubeUri = Uri.parse("vnd.youtube:" + trailer.key);
    final Uri webUri = new Uri.Builder()
        .scheme("https")
        .appendPath("youtube.com")
        .appendPath("watch")
        .appendQueryParameter("v", trailer.key)
        .build();
    final Intent webIntent = new Intent(Intent.ACTION_VIEW, webUri);
    final Intent youtubeIntent = new Intent(Intent.ACTION_VIEW, youtubeUri);
    if (youtubeIntent.resolveActivity(getPackageManager()) != null) {
      startActivity(youtubeIntent);
    } else {
      startActivity(webIntent);
    }
  }

  private final LoaderManager.LoaderCallbacks<Cursor> trailersCallback =
      new LoaderManager.LoaderCallbacks<Cursor>() {

        @Override public Loader<Cursor> onCreateLoader(int id, Bundle args) {
          final Uri trailersUri =
              ContentUris.withAppendedId(DatabaseContract.CONTENT_URI_TRAILERS, movie.id);
          return new CursorLoader(getApplicationContext(), trailersUri, null, null, null, null);
        }

        @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
          trailersAdapter.swapTrailersCursor(data);
        }

        @Override public void onLoaderReset(Loader<Cursor> loader) {
          trailersAdapter.swapTrailersCursor(null);
        }
      };

  private final LoaderManager.LoaderCallbacks<Cursor> reviewsCallback =
      new LoaderManager.LoaderCallbacks<Cursor>() {
        @Override public Loader onCreateLoader(int id, Bundle args) {
          final Uri reviewsUri =
              ContentUris.withAppendedId(DatabaseContract.CONTENT_URI_REVIEWS, movie.id);
          return new CursorLoader(getApplicationContext(), reviewsUri, null, null, null, null);
        }

        @Override public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
          reviewsAdapter.swapReviewsCursor(data);
        }

        @Override public void onLoaderReset(Loader<Cursor> loader) {
          reviewsAdapter.swapReviewsCursor(null);
        }
      };
}
