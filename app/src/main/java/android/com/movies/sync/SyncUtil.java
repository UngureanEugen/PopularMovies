package android.com.movies.sync;

import android.com.movies.data.DatabaseContract;
import android.com.movies.ui.movie.SortType;
import android.com.movies.util.AppExecutors;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;

import static android.com.movies.sync.SyncService.ACTION_SYNC_MOVIES;
import static android.com.movies.sync.SyncService.ACTION_SYNC_REVIEWS;
import static android.com.movies.sync.SyncService.ACTION_SYNC_TRAILERS;
import static android.com.movies.sync.SyncService.MOVIE_ID;
import static android.com.movies.sync.SyncService.SORT_TYPE;

public final class SyncUtil {
  private static AppExecutors executors = AppExecutors.getInstance();

  private SyncUtil() {
  }

  synchronized public static void initMoviesSync(final Context context,
      @SortType final int sortType) {
    executors.diskIO().execute(new Runnable() {
      @Override public void run() {
        Cursor cursor = context.getContentResolver().query(DatabaseContract.CONTENT_URI_MOVIES,
            null, null, null, DatabaseContract.getSort(sortType));
        if (null == cursor || cursor.getCount() == 0) {
          startMoviesImmediateSync(context, sortType);
        }
        if (cursor != null) {
          cursor.close();
        }
      }
    });
  }

  synchronized public static void initTrailersSync(@NonNull final Context context,
      final int movieId) {
    executors.diskIO().execute(new Runnable() {
      @Override public void run() {
        Cursor cursor = context.getContentResolver()
            .query(ContentUris.withAppendedId(DatabaseContract.CONTENT_URI_TRAILERS, movieId),
                null, null, null, null);
        if (null == cursor || cursor.getCount() == 0) {
          startTrailersImmediateSync(context, movieId);
        }
      }
    });
  }

  synchronized public static void initReviewsSync(final Context context, final Integer movieId) {
    executors.diskIO().execute(new Runnable() {
      @Override public void run() {
        Cursor cursor = context.getContentResolver()
            .query(ContentUris.withAppendedId(DatabaseContract.CONTENT_URI_REVIEWS, movieId),
                null, null, null, null);
        if (null == cursor || cursor.getCount() == 0) {
          startReviewsImmediateSync(context, movieId);
        }
      }
    });
  }

  private static void startTrailersImmediateSync(@NonNull Context context, int movieId) {
    Bundle bundle = new Bundle();
    bundle.putInt(MOVIE_ID, movieId);
    SyncService.fetch(context, bundle, ACTION_SYNC_TRAILERS);
  }

  public static void startMoviesImmediateSync(@NonNull Context context, @SortType int sortType) {
    if (sortType == SortType.TOP_RATED || sortType == SortType.MOST_POPULAR) {
      Bundle bundle = new Bundle();
      bundle.putInt(SORT_TYPE, sortType);
      SyncService.fetch(context, bundle, ACTION_SYNC_MOVIES);
    }
  }

  private static void startReviewsImmediateSync(Context context, Integer movieId) {
    Bundle bundle = new Bundle();
    bundle.putInt(MOVIE_ID, movieId);
    SyncService.fetch(context, bundle, ACTION_SYNC_REVIEWS);
  }
}
