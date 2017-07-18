package android.com.movies.data.remote.movie;

import android.com.movies.model.Video;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieVideosResponse {

  @SerializedName("id")
  @Expose
  public Integer movieId;

  @SerializedName("results")
  public List<Video> videos;
}
