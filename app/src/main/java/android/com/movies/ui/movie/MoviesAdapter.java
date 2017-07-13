package android.com.movies.ui.movie;

import android.com.movies.R;
import android.com.movies.di.NetworkModule;
import android.com.movies.model.MovieEntity;
import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.squareup.picasso.Picasso;
import java.util.List;

public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieViewHolder> {

  private final MovieClickCallback movieClickCallback;
  private List<MovieEntity> movies;

  public MoviesAdapter(MovieClickCallback movieClickCallback) {
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
    return movies == null ? 0 : movies.size();
  }

  public void setMovies(List<MovieEntity> movies) {
    this.movies = movies;
    notifyDataSetChanged();
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
      Picasso.with(context)
          .load(NetworkModule.IMAGE_URL + movies.get(position).posterPath)
          .noFade()
          .into(ivPoster);
      ViewCompat.setTransitionName(ivPoster, movies.get(position).title);
    }

    @Override
    public void onClick(View v) {
      movieClickCallback.onClick(movies.get(getAdapterPosition()), ivPoster);
    }
  }
}
