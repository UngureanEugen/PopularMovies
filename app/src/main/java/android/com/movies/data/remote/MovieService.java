package android.com.movies.data.remote;

import android.com.movies.data.remote.movie.MovieReviewsResponse;
import android.com.movies.data.remote.movie.MovieVideosResponse;
import android.com.movies.data.remote.movie.MoviesResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieService {

  @GET("movie/{sort}")
  Call<ApiResponse<MoviesResponse>> fetchMovies(@Path("sort") String path);

  @GET("movie/{id}/videos")
  Call<ApiResponse<MovieVideosResponse>> fetchVideos(@Path("id") String movieId);

  @GET("movie/{id}/reviews")
  Call<ApiResponse<MovieReviewsResponse>> fetchReviews(@Path("id") String movieId);
}
