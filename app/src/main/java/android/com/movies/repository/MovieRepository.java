package android.com.movies.repository;

public class MovieRepository {/*
  private final MovieDao movieDao;
  private final MovieService movieService;
  private final AppExecutors appExecutors;

  public MovieRepository(MovieDao movieDao, MovieService movieService) {
    this.movieDao = movieDao;
    this.movieService = movieService;
    appExecutors = AppExecutors.getInstance();
  }

  public LiveData<Resource<List<MovieEntity>>> getMovies(@SortType int type) {

    return new NetworkBoundResource<List<MovieEntity>, MoviesResponse>(appExecutors) {

      @Override
      protected void saveCallResult(@NonNull MoviesResponse response) {
        movieDao.insertMovies(response.movies);
      }

      @Override
      protected boolean shouldFetch(@Nullable List<MovieEntity> data) {
        //return data == null
        return true;
      }

      @NonNull
      @Override
      protected LiveData<List<MovieEntity>> loadFromDb() {
        return type == MOST_POPULAR ? movieDao.loadMostPopularMovies()
            : movieDao.loadTopRatedMovies();
      }

      @NonNull
      @Override
      protected LiveData<ApiResponse<MoviesResponse>> createCall() {
        final String path = type == MOST_POPULAR ? "popular" : "top_rated";
        return movieService.fetchMovies(path);
      }
    }.asLiveData();
  }

  public LiveData<Resource<List<Video>>> getVideos(int movieId) {
    return new NetworkBoundResource<List<Video>, MovieVideosResponse>(appExecutors) {

      @Override protected void saveCallResult(@NonNull MovieVideosResponse response) {
        for (Video videoItem : response.videos) {
          videoItem.movieId = response.movieId;
        }
        movieDao.insertVideos(response.videos);
      }

      @Override protected boolean shouldFetch(@Nullable List<Video> videos) {
        return videos == null || videos.size() == 0;
      }

      @NonNull @Override protected LiveData<List<Video>> loadFromDb() {
        return movieDao.loadVideos(movieId);
      }

      @NonNull @Override protected LiveData<ApiResponse<MovieVideosResponse>> createCall() {
        return movieService.fetchVideos(String.valueOf(movieId));
      }
    }.asLiveData();
  }

  public LiveData<Resource<List<Review>>> getReviews(int movieId) {
    return new NetworkBoundResource<List<Review>, MovieReviewsResponse>(appExecutors) {

      @Override protected void saveCallResult(@NonNull MovieReviewsResponse response) {
        for (Review item : response.reviews) {
          item.movieId = response.movieId;
        }
        movieDao.insertReviews(response.reviews);
      }

      @Override protected boolean shouldFetch(@Nullable List<Review> reviews) {
        return reviews == null || reviews.size() == 0;
      }

      @NonNull @Override protected LiveData<List<Review>> loadFromDb() {
        return movieDao.loadReviews(movieId);
      }

      @NonNull @Override protected LiveData<ApiResponse<MovieReviewsResponse>> createCall() {
        return movieService.fetchReviews(String.valueOf(movieId));
      }
    }.asLiveData();
}*/
}
