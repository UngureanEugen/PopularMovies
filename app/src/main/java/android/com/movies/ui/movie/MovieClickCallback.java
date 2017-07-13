package android.com.movies.ui.movie;

import android.com.movies.model.MovieEntity;
import android.widget.ImageView;

interface MovieClickCallback {
  void onClick(MovieEntity movie, ImageView sharedView);
}
