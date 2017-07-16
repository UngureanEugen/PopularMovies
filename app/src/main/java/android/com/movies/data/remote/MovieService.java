package android.com.movies.data.remote;

import android.arch.lifecycle.LiveData;
import retrofit2.http.GET;

public interface MovieService {

  @GET("movie/popular")
  LiveData<ApiResponse<MoviesResponse>> loadMostPopular();

  @GET("movie/top_rated")
  LiveData<ApiResponse<MoviesResponse>> loadTopRated();
}
