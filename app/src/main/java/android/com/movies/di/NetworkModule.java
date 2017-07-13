package android.com.movies.di;

import android.com.movies.data.remote.MovieService;
import android.com.movies.util.LiveDataCallAdapterFactory;
import android.com.movies.util.RequestInterceptor;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Module
public class NetworkModule {
  public static final String IMAGE_URL = "https://image.tmdb.org/t/p/w500/";

  @Provides
  @Singleton
  OkHttpClient provideOkHttpClient() {
    HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    logging.setLevel(HttpLoggingInterceptor.Level.BODY);

    return new OkHttpClient.Builder()
        .addInterceptor(new RequestInterceptor())
        .addInterceptor(logging)
        .build();
  }

  @Provides
  @Singleton
  public Retrofit provideRetrofit(OkHttpClient client) {
    String URL = "http://api.themoviedb.org/3/";
    return new Retrofit.Builder()
        .baseUrl(URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(new LiveDataCallAdapterFactory())
        .client(client)
        .build();
  }

  @Provides
  @Singleton
  public MovieService provideMovieService(Retrofit retrofit) {
    return retrofit.create(MovieService.class);
  }
}
