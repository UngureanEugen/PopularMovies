package android.com.movies.ui.movie;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;

import static android.com.movies.ui.movie.SortType.MOST_POPULAR;
import static android.com.movies.ui.movie.SortType.TOP_RATED;
import static java.lang.annotation.RetentionPolicy.SOURCE;

@IntDef({TOP_RATED, MOST_POPULAR})
@Retention(SOURCE) public @interface SortType {
  int TOP_RATED = 0;
  int MOST_POPULAR = 1;
  int NOT_DEFINED = 2;
}
