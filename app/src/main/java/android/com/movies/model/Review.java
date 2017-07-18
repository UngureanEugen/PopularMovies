package android.com.movies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
@Entity(tableName = "reviews")
public class Review {
  @PrimaryKey
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
  @ColumnInfo(name = "movie_id")
  public int movieId;
}
