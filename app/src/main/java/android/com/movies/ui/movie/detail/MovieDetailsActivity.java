package android.com.movies.ui.movie.detail;

import android.com.movies.R;
import android.com.movies.databinding.ActivityDetailsBinding;
import android.com.movies.di.NetworkModule;
import android.com.movies.model.MovieEntity;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import com.squareup.picasso.Picasso;

import static android.com.movies.ui.movie.MoviesActivity.EXTRA_MOVIE;
import static android.com.movies.ui.movie.MoviesActivity.EXTRA_MOVIE_IMAGE_TRANSITION;
import static android.com.movies.util.Utils.dpToPixel;

public class MovieDetailsActivity extends AppCompatActivity {

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
    MovieEntity movie = extras.getParcelable(EXTRA_MOVIE);
    if (movie != null) {
      ViewCompat.setElevation(binding.ltTitle, dpToPixel(this, 6f));
      binding.tvTitle.setText(movie.title);
      binding.tvReleaseDate.setText(movie.releaseDate);
      binding.tvVote.setText(String.format("%s/10", movie.voteAverage));
      binding.tvPlotSynopsis.setText(movie.overview);
      Picasso.with(this)
          .load(NetworkModule.IMAGE_URL + movie.posterPath)
          .noFade()
          .into(binding.ivPoster);
    } else {
      finish();
    }
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
}
