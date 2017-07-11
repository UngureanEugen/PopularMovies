package android.com.movies.di;

import android.app.Application;
import android.arch.persistence.room.Room;
import android.com.movies.data.local.AppDatabase;
import android.com.movies.data.local.dao.MovieDao;
import android.com.movies.data.remote.MovieService;
import android.com.movies.repository.MovieRepository;
import android.content.Context;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private Application app;

    public AppModule(Application app) {
        this.app = app;
    }

    @Provides
    @Singleton
    public Context provideContext() {
        return app;
    }

    @Provides
    @Singleton
    public Application provideApplication() {
        return app;
    }

    @Provides
    @Singleton
    public AppDatabase provideMovieDatabase(Context context) {
        // Reset the database to have new data on every run.
        context.deleteDatabase(AppDatabase.DATABASE_NAME);
        // Build the database!
        return Room.databaseBuilder(context.getApplicationContext(),
                AppDatabase.class, AppDatabase.DATABASE_NAME).build();

    }

    @Provides
    public MovieDao provideMovieDao(AppDatabase database) {
        return database.movieDao();
    }

    @Provides
    @Singleton
    public MovieRepository provideMovieRepository(MovieDao dao, MovieService service) {
        return new MovieRepository(dao, service);
    }


}
