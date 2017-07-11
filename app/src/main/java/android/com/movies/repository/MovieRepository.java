package android.com.movies.repository;

import android.arch.lifecycle.LiveData;
import android.com.movies.AppExecutors;
import android.com.movies.data.NetworkBoundResource;
import android.com.movies.data.Resource;
import android.com.movies.data.local.dao.MovieDao;
import android.com.movies.data.remote.ApiResponse;
import android.com.movies.data.remote.MovieEntity;
import android.com.movies.data.remote.MovieService;
import android.com.movies.data.remote.MoviesResponse;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

public class MovieRepository {
    private final MovieDao movieDao;
    private final MovieService movieService;
    private final AppExecutors appExecutors;

    public MovieRepository(MovieDao movieDao, MovieService movieService) {
        this.movieDao = movieDao;
        this.movieService = movieService;
        appExecutors = AppExecutors.getInstance();
    }

    public LiveData<Resource<List<MovieEntity>>> getPopularMovies() {

        return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull MoviesResponse response) {
                movieDao.insertMovies(response.movies);
            }

            @Override
            protected boolean shouldFetch(@Nullable List<MovieEntity> data) {
                return data == null || data.isEmpty();
            }

            @NonNull
            @Override
            protected LiveData<List<MovieEntity>> loadFromDb() {
                return movieDao.loadPopular();
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<MoviesResponse>> createCall() {
                return movieService.loadPopular("popularity.desc");
            }
        }.asLiveData();
    }
}