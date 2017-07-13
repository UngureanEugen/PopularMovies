package android.com.movies.di;

import android.com.movies.viewmodel.MovieViewModel;
import dagger.Component;
import javax.inject.Singleton;

@Singleton()
@Component(modules = {AppModule.class, NetworkModule.class})
public interface MoviesComponent {
  void inject(MovieViewModel viewModel);
}
