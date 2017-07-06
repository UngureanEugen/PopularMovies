package android.com.movies.data.remote;

import android.arch.lifecycle.LiveData;

import java.util.List;

import retrofit2.http.GET;

public interface MovieService {
    @GET("movie/popular")
    LiveData<ApiResponse<List<MovieEntity>>> loadPopular();

    @GET("movie/top_rated")
    LiveData<ApiResponse<List<MovieEntity>>> loadTopRated();
}
