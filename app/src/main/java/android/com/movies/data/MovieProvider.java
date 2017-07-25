package android.com.movies.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MovieProvider extends ContentProvider {

  private static final String TAG = MovieProvider.class.getSimpleName();

  private static final int MOVIES = 100;
  private static final int MOVIE_WITH_ID = 101;
  private static final int MOST_POPULAR_MOVIES = 102;
  private static final int TOP_RATED_MOVIES = 103;
  private static final int FAVORITES_MOVIES = 104;

  private ProviderDatabaseHelper mDbHelper;

  private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

  static {
    // content://android.com.movies/movies
    sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.TABLE_MOVIES, MOVIES);

    // content://android.com.movies/movies/id
    sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.TABLE_MOVIES + "/#",
        MOVIE_WITH_ID);

    // content://android.com.movies/movies/top_rated
    sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.TABLE_MOVIES + "/" +
        DatabaseContract.PATH_TOP_RATED, TOP_RATED_MOVIES);

    // content://android.com.movies/movies/most_popular
    sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.TABLE_MOVIES + "/" +
        DatabaseContract.PATH_MOST_POPULAR, MOST_POPULAR_MOVIES);

    // content://android.com.movies/movies/favorites
    sUriMatcher.addURI(DatabaseContract.CONTENT_AUTHORITY, DatabaseContract.TABLE_MOVIES + "/" +
        DatabaseContract.PATH_FAVORITES, FAVORITES_MOVIES);
  }

  @Override public boolean onCreate() {
    mDbHelper = ProviderDatabaseHelper.getInstance(getContext());
    return true;
  }

  @Nullable @Override
  public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection,
      @Nullable String[] selectionArgs, @Nullable String sortOrder) {
    final Cursor result;
    switch (sUriMatcher.match(uri)) {
      case MOVIES:
        //Rows aren't counted with null selection
        selection = (selection == null) ? "1" : selection;
        break;
      case MOVIE_WITH_ID:
        long id = ContentUris.parseId(uri);
        selection = String.format("%s = ?", DatabaseContract.MovieColumns._ID);
        selectionArgs = new String[] {String.valueOf(id)};
        break;
      default:
        throw new IllegalArgumentException("Illegal delete URI");
    }
    SQLiteDatabase db = mDbHelper.getReadableDatabase();
    result =
        db.query(DatabaseContract.TABLE_MOVIES, projection, selection, selectionArgs, null, null,
            sortOrder);
    result.setNotificationUri(getContext().getContentResolver(), uri);
    return result;
  }

  @Nullable @Override public String getType(@NonNull Uri uri) {
    return null;  /* Not used */
  }

  @Nullable @Override public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
    final SQLiteDatabase db = mDbHelper.getWritableDatabase();
    int match = sUriMatcher.match(uri);
    Uri returnUri;
    switch (match) {
      case MOVIES:
        long id = db.insert(DatabaseContract.TABLE_MOVIES, null, values);
        if (id > 0) {
          returnUri = ContentUris.withAppendedId(DatabaseContract.CONTENT_URI, id);
        } else {
          throw new SQLException("Failed to insert row into " + uri);
        }
        break;
      default:
        throw new UnsupportedOperationException("Unknown uri: " + uri);
    }
    getContext().getContentResolver().notifyChange(uri, null);
    return returnUri;
  }

  @Override public int delete(@NonNull Uri uri, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    switch (sUriMatcher.match(uri)) {
      case MOVIES:
        selection = (selection == null) ? "1" : selection;
        break;
      case MOVIE_WITH_ID:
        long id = ContentUris.parseId(uri);
        selection = String.format("%s = ?", DatabaseContract.MovieColumns._ID);
        selectionArgs = new String[] {String.valueOf(id)};
        break;
      default:
        throw new IllegalArgumentException("Illegal delete URI");
    }
    SQLiteDatabase db = mDbHelper.getWritableDatabase();
    int count = db.delete(DatabaseContract.TABLE_MOVIES, selection, selectionArgs);
    if (count > 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return count;
  }

  @Override
  public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection,
      @Nullable String[] selectionArgs) {
    switch (sUriMatcher.match(uri)) {
      case MOVIE_WITH_ID:
        long id = ContentUris.parseId(uri);
        selection = String.format("%s = ? ", DatabaseContract.MovieColumns._ID);
        selectionArgs = new String[] {String.valueOf(id)};
        break;
      default:
        throw new IllegalArgumentException("Illegal update URI");
    }
    SQLiteDatabase db = mDbHelper.getWritableDatabase();
    int count = db.update(DatabaseContract.TABLE_MOVIES, values, selection, selectionArgs);
    if (count > 0) {
      getContext().getContentResolver().notifyChange(uri, null);
    }
    return count;
  }
}
