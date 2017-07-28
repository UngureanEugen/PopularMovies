package android.com.movies.di;

import android.com.movies.sync.SyncService;
import dagger.Component;
import javax.inject.Singleton;

@Singleton()
@Component(modules = {AppModule.class, NetworkModule.class})
public interface MoviesComponent {
  void inject(SyncService syncService);
}
