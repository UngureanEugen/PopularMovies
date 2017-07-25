package android.com.movies.sync;

import android.com.movies.data.DatabaseContract;
import android.com.movies.ui.movie.SortType;
import android.com.movies.util.AppExecutors;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.annotation.NonNull;

public final class SyncUtil {
  private static AppExecutors executors = AppExecutors.getInstance();
  private static boolean initialized;
  private static @SortType int type = SortType.NOT_DEFINED;

  private SyncUtil() {
  }

  synchronized public static void init(Context context) {
    if (initialized) return;
    initialized = true;
    executors.diskIO().execute(() -> {

                /* Here, we perform the query to check to see if we have any weather data */
      Cursor cursor = context.getContentResolver().query(DatabaseContract.CONTENT_URI,
          null, null, null, null);
      if (null == cursor || cursor.getCount() == 0) {
        startImmediateSync();
      }
      if (cursor != null) {
        cursor.close();
      }
    });
  }
¡

  private static void startImmediateSync(@NonNull Context context) {
    Intent syncIntent = new Intent(context, SyncService.class);
    context.startService(syncIntent);
  }
}
