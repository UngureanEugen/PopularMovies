package android.com.movies.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.com.movies.data.DatabaseContract.ReviewColumns;
import static android.com.movies.data.DatabaseContract.getColumnInt;
import static android.com.movies.data.DatabaseContract.getColumnString;

@SuppressWarnings("ALL")
public class Review {
  @SerializedName("id")
  @Expose
  public String id;
  @SerializedName("author")
  @Expose
  public String author;
  @SerializedName("content")
  @Expose
  public String content;
  @SerializedName("url")
  @Expose
  public String url;
  public int movieId;

  public Review(Cursor cursor) {
    this.id = getColumnString(cursor, ReviewColumns._ID);
    this.author = getColumnString(cursor, ReviewColumns.AUTHOR);
    this.content = getColumnString(cursor, ReviewColumns.CONTENT);
    this.url = getColumnString(cursor, ReviewColumns.URL);
    this.movieId = getColumnInt(cursor, ReviewColumns.MOVIE_ID);
  }

  public static ContentValues convert(Review review, int movieId) {
    ContentValues content = new ContentValues();
    content.put(ReviewColumns._ID, review.id);
    content.put(ReviewColumns.AUTHOR, review.author);
    content.put(ReviewColumns.CONTENT, review.content);
    content.put(ReviewColumns.MOVIE_ID, movieId);
    content.put(ReviewColumns.URL, review.url);
    return content;
  }
}
