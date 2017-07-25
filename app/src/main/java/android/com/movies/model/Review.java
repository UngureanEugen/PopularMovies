package android.com.movies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
}
