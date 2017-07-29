package android.com.movies.model;

import android.content.ContentValues;
import android.database.Cursor;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.com.movies.data.DatabaseContract.TrailerColumns;
import static android.com.movies.data.DatabaseContract.getColumnInt;
import static android.com.movies.data.DatabaseContract.getColumnString;

@SuppressWarnings("ALL")
public class Video {
  @SerializedName("id")
  @Expose
  public String id;
  @SerializedName("iso_639_1")
  @Expose
  public String iso6391;
  @SerializedName("iso_3166_1")
  @Expose
  public String iso31661;
  @SerializedName("key")
  @Expose
  public String key;
  @SerializedName("name")
  @Expose
  public String name;
  @SerializedName("site")
  @Expose
  public String site;
  @SerializedName("size")
  @Expose
  public Integer size;
  @SerializedName("type")
  @Expose
  public String type;
  public int movieId;

  public Video(Cursor cursor) {
    this.id = getColumnString(cursor, TrailerColumns._ID);
    this.iso6391 = getColumnString(cursor, TrailerColumns.ISO6391);
    this.iso31661 = getColumnString(cursor, TrailerColumns.ISO31661);
    this.key = getColumnString(cursor, TrailerColumns.KEY);
    this.name = getColumnString(cursor, TrailerColumns.NAME);
    this.site = getColumnString(cursor, TrailerColumns.SITE);
    this.size = getColumnInt(cursor, TrailerColumns.SIZE);
    this.type = getColumnString(cursor, TrailerColumns.TYPE);
    this.movieId = getColumnInt(cursor, TrailerColumns.MOVIE_ID);
  }

  public static ContentValues convert(Video video, int movieId) {
    ContentValues content = new ContentValues();
    content.put(TrailerColumns._ID, video.id);
    content.put(TrailerColumns.ISO6391, video.iso6391);
    content.put(TrailerColumns.ISO31661, video.iso31661);
    content.put(TrailerColumns.KEY, video.key);
    content.put(TrailerColumns.NAME, video.name);
    content.put(TrailerColumns.SITE, video.site);
    content.put(TrailerColumns.SIZE, video.size);
    content.put(TrailerColumns.TYPE, video.type);
    content.put(TrailerColumns.MOVIE_ID, movieId);
    return content;
  }
}
