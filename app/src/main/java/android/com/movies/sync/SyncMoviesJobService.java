package android.com.movies.sync;

import android.com.movies.data.DatabaseContract;
import android.com.movies.data.remote.MovieService;
import android.com.movies.data.remote.movie.MoviesResponse;
import android.com.movies.util.AppExecutors;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;
import java.io.IOException;
import javax.inject.Inject;
import retrofit2.Response;

public class SyncMoviesJobService extends JobService {
  @Inject MovieService movieService;
  AppExecutors executors;
  Context context;

  public SyncMoviesJobService(Context context) {
    this.context = context;
  }

  @Override public boolean onStartJob(JobParameters job) {
    executors = AppExecutors.getInstance();
    Bundle bundle = job.getExtras();
    if (bundle != null) {
      Cursor cursor = context.getContentResolver().query(
          DatabaseContract.CONTENT_URI_MOVIES, null, null, null, null);
      if (cursor != null) {

      }
      String key = bundle.getString("", "");
      executors.networkIO().execute(() -> {
        try {
          Response<MoviesResponse> response = movieService.fetchMovies(key).execute();
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    }
    return false;
  }

  @Override public boolean onStopJob(JobParameters job) {
    return false;
  }
}
