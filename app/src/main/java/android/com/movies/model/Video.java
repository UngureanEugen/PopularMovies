package android.com.movies.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

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
}
