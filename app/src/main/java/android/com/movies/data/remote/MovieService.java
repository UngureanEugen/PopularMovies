package android.com.movies.data.remote;

import android.arch.lifecycle.LiveData;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MovieService {
/*
    "http://api.themoviedb.org/3/
    http://api.themoviedb.org/3/discover/movie?sort_by=popularity.desc&api_key=6d051b9ef75d7e7b167978fb2e967804
*/

    @GET("discover/movie")
    LiveData<ApiResponse<MoviesResponse>> loadPopular(@Query("sort_by") String sort);

    @GET("movie/top_rated")
    LiveData<ApiResponse<List<MovieEntity>>> loadTopRated();
}
