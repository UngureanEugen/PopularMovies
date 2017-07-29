package android.com.movies.sync;

import android.app.IntentService;
import android.com.movies.App;
import android.com.movies.data.DatabaseContract;
import android.com.movies.data.remote.MovieService;
import android.com.movies.data.remote.movie.MovieReviewsResponse;
import android.com.movies.data.remote.movie.MovieVideosResponse;
import android.com.movies.data.remote.movie.MoviesResponse;
import android.com.movies.model.MovieEntity;
import android.com.movies.model.Review;
import android.com.movies.model.Video;
import android.com.movies.ui.movie.SortType;
import android.com.movies.util.AppExecutors;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncService extends IntentService {
  @Inject
  MovieService service;

  private static final String TAG = SyncService.class.getSimpleName();
  private static final AppExecutors executors = AppExecutors.getInstance();
  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   */
  public static final String ACTION_UPDATE_MOVIE = TAG + ".updateMovie";
  public static final String ACTION_SYNC_MOVIES = TAG + ".syncMovies";
  public static final String ACTION_SYNC_TRAILERS = TAG + ".syncTrailers";
  public static final String ACTION_SYNC_REVIEWS = TAG + ".syncReviews";
  public static final String SORT_TYPE = TAG + ".sortType";
  public static final String MOVIE_ID = TAG + ".movieId";
  public static final String EXTRA = TAG + ".contentValues";

  public SyncService(String name) {
    super(name);
  }

  public SyncService() {
    super(TAG);
  }

  @Override public void onCreate() {
    super.onCreate();
    App.moviesComponent.inject(this);
  }

  public static void updateMovie(Context context, Uri uri, ContentValues values) {
    Intent intent = new Intent(context, SyncService.class);
    intent.setAction(ACTION_UPDATE_MOVIE);
    intent.setData(uri);
    intent.putExtra(EXTRA, values);
    context.startService(intent);
  }

  public static void fetch(Context context, Bundle bundle, String action) {
    Intent intent = new Intent(context, SyncService.class);
    intent.setAction(action);
    intent.putExtras(bundle);
    context.startService(intent);
  }

  @Override protected void onHandleIntent(@Nullable Intent intent) {
    if (ACTION_SYNC_MOVIES.equals(intent.getAction())) {
      performSyncMovies(intent.getExtras());
    } else if (ACTION_SYNC_TRAILERS.equals(intent.getAction())) {
      performSyncTrailers(intent.getExtras());
    } else if (ACTION_SYNC_REVIEWS.equals(intent.getAction())) {
      performSyncReviews(intent.getExtras());
    } else if (ACTION_UPDATE_MOVIE.equals(intent.getAction())) {
      ContentValues values = intent.getParcelableExtra(EXTRA);
      performUpdate(intent.getData(), values);
    }
  }

  private void performUpdate(Uri uri, ContentValues values) {
    executors.diskIO().execute(() -> {
      int count = getContentResolver().update(uri, values, null, null);
      Log.e(TAG, "updated items count " + count);
    });
  }

  private void performSyncMovies(Bundle extras) {
    @SortType int sortType = extras.getInt(SORT_TYPE, SortType.NOT_DEFINED);
    executors.networkIO().execute(() -> {
      service.fetchMovies(sortType == SortType.TOP_RATED ? "top_rated" : "popular")
          .enqueue(new Callback<MoviesResponse>() {
            @Override public void onResponse(Call<MoviesResponse> call,
                Response<MoviesResponse> response) {
              if (response.isSuccessful() && response.body() != null) {
                List<MovieEntity> movies = response.body().movies;
                if (movies != null) {
                  ContentValues[] contentValues = new ContentValues[movies.size()];
                  for (int i = 0; i < movies.size(); i++) {
                    contentValues[i] = MovieEntity.convert(movies.get(i));
                  }
                  getApplicationContext().getContentResolver()
                      .bulkInsert(DatabaseContract.CONTENT_URI_MOVIES, contentValues);
                }
              }
            }

            @Override public void onFailure(Call<MoviesResponse> call, Throwable t) {
              Log.e(TAG, t.getMessage());
            }
          });
    });
  }

  private void performSyncTrailers(Bundle extras) {
    int movieId = extras.getInt(MOVIE_ID);
    executors.networkIO().execute(() -> {
      service.fetchVideos(String.valueOf(movieId)).enqueue(new Callback<MovieVideosResponse>() {
        @Override
        public void onResponse(Call<MovieVideosResponse> call,
            Response<MovieVideosResponse> response) {
          if (response.isSuccessful() && response.body() != null) {
            List<Video> trailers = response.body().videos;
            if (trailers != null) {
              ContentValues[] values = new ContentValues[trailers.size()];
              for (int i = 0; i < trailers.size(); i++) {
                values[i] = Video.convert(trailers.get(i), movieId);
              }
              getApplicationContext().getContentResolver()
                  .bulkInsert(DatabaseContract.CONTENT_URI_TRAILERS, values);
            }
          }
        }

        @Override public void onFailure(Call<MovieVideosResponse> call, Throwable t) {
          Log.d(TAG, "onFailure parsing");
        }
      });
    });
  }

  private void performSyncReviews(Bundle extras) {
    int movieId = extras.getInt(MOVIE_ID);
    executors.networkIO().execute(() -> {
      service.fetchReviews(String.valueOf(movieId)).enqueue(new Callback<MovieReviewsResponse>() {
        @Override
        public void onResponse(Call<MovieReviewsResponse> call,
            Response<MovieReviewsResponse> response) {
          if (response.isSuccessful() && response.body() != null) {
            List<Review> reviews = response.body().reviews;
            if (reviews != null) {
              ContentValues[] values = new ContentValues[reviews.size()];
              for (int i = 0; i < reviews.size(); i++) {
                values[i] = Review.convert(reviews.get(i), movieId);
              }
              getApplicationContext().getContentResolver()
                  .bulkInsert(DatabaseContract.CONTENT_URI_REVIEWS, values);
            }
          }
        }

        @Override public void onFailure(Call<MovieReviewsResponse> call, Throwable t) {
          Log.d(TAG, "onFailure parsing");
        }
      });
    });
  }
}
