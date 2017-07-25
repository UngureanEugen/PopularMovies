package android.com.movies.sync;

import android.app.IntentService;
import android.com.movies.data.remote.ApiResponse;
import android.com.movies.data.remote.MovieService;
import android.com.movies.data.remote.movie.MoviesResponse;
import android.com.movies.model.MovieEntity;
import android.com.movies.ui.movie.SortType;
import android.com.movies.util.AppExecutors;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import java.util.List;
import javax.inject.Inject;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SyncService extends IntentService {
  @Inject static MovieService service;

  private static final String TAG = SyncService.class.getSimpleName();
  private static final AppExecutors executors = AppExecutors.getInstance();
  /**
   * Creates an IntentService.  Invoked by your subclass's constructor.
   *
   * @param name Used to name the worker thread, important only for debugging.
   */
  public static final String ACTION_INSERT = TAG + "#insert";
  public static final String ACTION_UPDATE = TAG + "#update";
  public static final String ACTION_SYNC = TAG + "#sync";
  public static final String SORT_TYPE = TAG + "#sortType";

  public SyncService(String name) {
    super(name);
  }

  public static void update(Context context) {
    Intent intent = new Intent(context, SyncService.class);
    intent.setAction(ACTION_UPDATE);
    context.startService(intent);
  }

  public static void fetchMovies(Context context, @SortType int sort) {
    Bundle bundle = new Bundle();
    bundle.putInt(SORT_TYPE, sort);
    Intent intent = new Intent(context, SyncService.class);
    intent.setAction(Intent.ACTION_SYNC);
    intent.putExtras(bundle);
    context.startService(intent);
  }

  @Override protected void onHandleIntent(@Nullable Intent intent) {
    if (ACTION_SYNC.equals(intent.getAction())) {
      performSync(intent.getExtras());
    }
  }

  private void performSync(Bundle extras) {
    @SortType int sortType = extras.getInt(SORT_TYPE, SortType.NOT_DEFINED);
    executors.networkIO().execute(() -> {
      service.fetchMovies(sortType == SortType.TOP_RATED ? "top_rated" : "popular")
          .enqueue(new Callback<ApiResponse<MoviesResponse>>() {
            @Override public void onResponse(Call<ApiResponse<MoviesResponse>> call,
                Response<ApiResponse<MoviesResponse>> response) {
              if (response.isSuccessful() && response.body() != null) {
                List<MovieEntity> movies = response.body().body.movies;
                if (movies != null) {
                  ContentValues[] contentValues = new ContentValues[movies.size()];
                  for (int i = 0; i < movies.size(); i++) {
                    contentValues[i] = MovieEntity.convert(movies.get(i));
                  }
                }
                // TODO: 7/26/17 bulk insert
              }
            }

            @Override public void onFailure(Call<ApiResponse<MoviesResponse>> call, Throwable t) {

            }
          });
    });
  }
}
