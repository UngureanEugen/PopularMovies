package android.com.movies.data.remote;

import android.com.movies.model.MovieEntity;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MoviesResponse {
  @SerializedName("results")
  public List<MovieEntity> movies;
}
