package android.com.movies.data.remote;

import android.arch.lifecycle.LiveData;
import android.com.movies.model.MovieEntity;
import java.util.List;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {

  @GET("discover/movie")
  LiveData<ApiResponse<MoviesResponse>> loadMovies(@Query("sort_by") String sort);

  @GET("movie/top_rated")
  LiveData<ApiResponse<List<MovieEntity>>> loadTopRated();
}
