package android.com.movies.ui.movie.detail;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.com.movies.R;
import android.com.movies.databinding.ActivityDetailsBinding;
import android.com.movies.di.NetworkModule;
import android.com.movies.model.MovieEntity;
import android.com.movies.model.Video;
import android.com.movies.viewmodel.MovieViewModel;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
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
    implements LifecycleRegistryOwner, TrailersAdapter.OnTrailerClickListener {

  private final LifecycleRegistry lifecycleRegistry = new LifecycleRegistry(this);
  private MovieViewModel movieViewModel;
  private TrailersAdapter trailersAdapter;
  private ReviewsAdapter reviewsAdapter;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    ActivityDetailsBinding binding =
        DataBindingUtil.setContentView(this, R.layout.activity_details);

    movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

    Bundle extras = getIntent().getExtras();
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      String transition = extras.getString(EXTRA_MOVIE_IMAGE_TRANSITION);
      binding.ivPoster.setTransitionName(transition);
    }
    MovieEntity movie = extras.getParcelable(EXTRA_MOVIE);
    if (movie != null) {
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
      subscribeUi();
      movieViewModel.setMovieId(movie.id);
    } else {
      finish();
    }
  }

  private void subscribeUi() {
    movieViewModel.getVideos().observe(this, trailers -> {
      if (trailers != null && trailers.data != null) {
        trailersAdapter.setTrailers(trailers.data);
      }
    });
    movieViewModel.getReviews().observe(this, reviews -> {
      if (reviews != null && reviews.data != null) {
        reviewsAdapter.setReviews(reviews.data);
      }
    });
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

  @Override public LifecycleRegistry getLifecycle() {
    return lifecycleRegistry;
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
}
