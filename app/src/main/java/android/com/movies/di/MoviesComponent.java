package android.com.movies.di;

import android.com.movies.viewmodel.MovieViewModel;

import javax.inject.Singleton;

import dagger.Component;

@Singleton()
@Component(modules = {AppModule.class, NetworkModule.class})
public interface MoviesComponent {
    void inject(MovieViewModel viewModel);
}
