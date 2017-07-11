package android.com.movies.ui.movie;

import android.com.movies.R;
import android.com.movies.data.remote.MovieEntity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
        return 0;
    }

    public void setMovies(List<MovieEntity> movies) {
        this.movies = movies;
    }

    public class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public MovieViewHolder(View itemView) {
            super(itemView);
            //init views
            itemView.setOnClickListener(this);
        }


        public void bind(int position) {
            // set values
        }

        @Override
        public void onClick(View v) {
//            movieClickCallback.onClick(getAdapterPosition());
        }
    }
}
