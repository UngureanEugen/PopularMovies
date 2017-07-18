package android.com.movies.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL")
@Entity(tableName = "videos")
public class Video {
  @PrimaryKey
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
  @ColumnInfo(name = "movie_id")
  public int movieId;
}
