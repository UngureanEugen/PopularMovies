package android.com.movies.viewmodel;

public class MovieViewModel {/*extends AndroidViewModel {

  private final MutableLiveData<Integer> sortType = new MutableLiveData<>();
  private final MutableLiveData<Integer> movieId = new MutableLiveData<>();

  private final LiveData<Resource<List<MovieEntity>>> movies =
      Transformations.switchMap(sortType,
          sort -> repository.getMovies(sort));

  private final LiveData<Resource<List<Video>>> videos =
      Transformations.switchMap(movieId, movieId -> repository.getVideos(movieId));
  private final LiveData<Resource<List<Review>>> reviews =
      Transformations.switchMap(movieId, movieId -> repository.getReviews(movieId));

  @Inject public MovieViewModel(Application application) {
    super(application);
    App.moviesComponent.inject(this);
  }

  public LiveData<Resource<List<MovieEntity>>> getMovies() {
    return movies;
  }

  public LiveData<Resource<List<Video>>> getVideos() {
    return videos;
  }

  public LiveData<Resource<List<Review>>> getReviews() {
    return reviews;
  }

  public void setSortType(@SortType int type) {
    sortType.setValue(type);
  }

  public void setMovieId(@SortType int type) {
    movieId.setValue(type);
  }*/
}
