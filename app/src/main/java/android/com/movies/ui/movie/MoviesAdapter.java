package android.com.movies.ui.movie;

import android.com.movies.R;
import android.com.movies.di.NetworkModule;
import android.com.movies.model.MovieEntity;
import android.content.Context;
import android.database.Cursor;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

  private final MovieClickCallback movieClickCallback;
  private Cursor cursor;

  public MoviesAdapter(Cursor cursor, MovieClickCallback movieClickCallback) {
    this.cursor = cursor;
    this.movieClickCallback = movieClickCallback;
  }

  @Override
  public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.movie_list_item, parent, false);
    return new MovieViewHolder(view);
  }

  @Override
  public void onBindViewHolder(MovieViewHolder holder, int position) {
    holder.bind(position);
  }

  @Override
  public int getItemCount() {
    return (cursor != null) ? cursor.getCount() : 0;
  }

  public void swapCursor(Cursor data) {
    if (cursor != null) {
      cursor.close();
    }
    cursor = data;
    notifyDataSetChanged();
  }

  public MovieEntity getMovie(int position) {
    if (!cursor.moveToPosition(position)) {
      throw new IllegalArgumentException("Invalid position");
    }
    return new MovieEntity(cursor);
  }

  public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private final ImageView ivPoster;
    private final Context context;

    public MovieViewHolder(View itemView) {
      super(itemView);
      //init views
      ivPoster = (ImageView) itemView.findViewById(R.id.iv_poster);
      itemView.setOnClickListener(this);
      context = itemView.getContext();
    }

    public void bind(int position) {
      // set values
      MovieEntity movieEntity = getMovie(position);
      Picasso.with(context)
          .load(NetworkModule.IMAGE_URL + movieEntity.posterPath)
          .noFade()
          .into(ivPoster);
      ViewCompat.setTransitionName(ivPoster, movieEntity.title);
    }

    @Override
    public void onClick(View v) {
      movieClickCallback.onClick(getMovie(getAdapterPosition()), ivPoster);
    }
  }
}
