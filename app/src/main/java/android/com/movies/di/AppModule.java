package android.com.movies.di;

import android.app.Application;
import android.content.Context;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;

@Module
public class AppModule {
  private final Application app;

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

/*  @Provides
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
  }*/
}
