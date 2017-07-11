package android.com.movies;

import android.app.Application;
import android.com.movies.di.AppModule;
import android.com.movies.di.DaggerMoviesComponent;
import android.com.movies.di.MoviesComponent;

public class App extends Application {
    public static MoviesComponent moviesComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        moviesComponent = DaggerMoviesComponent.builder()
                .appModule(new AppModule(this))
                .build();
    }
}
