package android.com.movies.data.remote;

import android.arch.lifecycle.LiveData;
import android.com.movies.data.remote.movie.MovieReviewsResponse;
import android.com.movies.data.remote.movie.MovieVideosResponse;
import android.com.movies.data.remote.movie.MoviesResponse;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface MovieService {

  @GET("movie/{sort}")
  LiveData<ApiResponse<MoviesResponse>> fetchMovies(@Path("sort") String path);

  @GET("movie/{id}/videos")
  LiveData<ApiResponse<MovieVideosResponse>> fetchVideos(@Path("id") String movieId);

  @GET("movie/{id}/reviews")
  LiveData<ApiResponse<MovieReviewsResponse>> fetchReviews(@Path("id") String movieId);
}
