package android.com.movies.data.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.com.movies.data.provider.DatabaseContract.MovieColumns;
import static android.com.movies.data.provider.DatabaseContract.ReviewColumns;
import static android.com.movies.data.provider.DatabaseContract.TABLE_MOVIES;
import static android.com.movies.data.provider.DatabaseContract.TABLE_REVIEWS;
import static android.com.movies.data.provider.DatabaseContract.TABLE_TRAILERS;
import static android.com.movies.data.provider.DatabaseContract.TrailerColumns;

public class ProviderDatabaseHelper extends SQLiteOpenHelper {
  private static ProviderDatabaseHelper instance;

  private static final String DATABASE_NAME = "moviesProviderDatabase";
  private static final int DATABASE_VERSION = 1;

  private static final String SQL_CREATE_TABLE_MOVIES = String.format("CREATE TABLE %s"
          + " (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s BOOLEAN, %s BOOLEAN, %s TEXT, %s TEXT,"
          + "%s TEXT, %s DOUBLE, %s TEXT, %s TEXT, %s TEXT,   %s DOUBLE, %s INTEGER)",
      DatabaseContract.TABLE_MOVIES,
      MovieColumns._ID,
      MovieColumns.BACKDROP_PATH,
      MovieColumns.HAS_VIDEO,
      MovieColumns.IS_ADULT,
      MovieColumns.ORIGINAL_LANGUAGE,
      MovieColumns.ORIGINAL_TITLE,
      MovieColumns.OVERVIEW,
      MovieColumns.POPULARITY,
      MovieColumns.POSTER_PATH,
      MovieColumns.RELEASE_DATE,
      MovieColumns.TITLE,
      MovieColumns.VOTE_AVERAGE,
      MovieColumns.VOTE_COUNT
  );

  private static final String SQL_CREATE_TABLE_REVIEWS = String.format("CREATE TABLE %s"
          + " (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s INTEGER, %s TEXT)",
      DatabaseContract.TABLE_REVIEWS,
      ReviewColumns._ID,
      ReviewColumns.AUTHOR,
      ReviewColumns.CONTENT,
      ReviewColumns.MOVIE_ID,
      ReviewColumns.URL
  );

  private static final String SQL_CREATE_TABLE_TRAILERS = String.format("CREATE TABLE %s"
          + " (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT,  %s TEXT, %s TEXT, %s INTEGER, %s TEXT, %s INTEGER, %s TEXT)",
      DatabaseContract.TABLE_TRAILERS,
      TrailerColumns._ID,
      TrailerColumns.ISO6391,
      TrailerColumns.ISO31661,
      TrailerColumns.KEY,
      TrailerColumns.NAME,
      TrailerColumns.MOVIE_ID,
      TrailerColumns.SITE,
      TrailerColumns.SIZE,
      TrailerColumns.TYPE
  );

  public static synchronized ProviderDatabaseHelper getInstance(Context context) {
    if (instance == null) {
      instance = new ProviderDatabaseHelper(context.getApplicationContext());
    }
    return instance;
  }

  private ProviderDatabaseHelper(Context context) {
    super(context, DATABASE_NAME, null, DATABASE_VERSION);
  }

  @Override public void onConfigure(SQLiteDatabase db) {
    super.onConfigure(db);
    db.setForeignKeyConstraintsEnabled(true);
  }

  @Override public void onCreate(SQLiteDatabase db) {
    db.execSQL(SQL_CREATE_TABLE_MOVIES);
    db.execSQL(SQL_CREATE_TABLE_REVIEWS);
    db.execSQL(SQL_CREATE_TABLE_TRAILERS);
  }

  @Override public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    if (oldVersion != newVersion) {
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVIES);
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_REVIEWS);
      db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRAILERS);
      onCreate(db);
    }
  }
}
