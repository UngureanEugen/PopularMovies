package android.com.movies.ui.movie;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;


public class MoviesAdapter extends RecyclerView.Adapter<Object> {
    private final MovieClickCallback movieClickCallback;

    public MoviesAdapter(MovieClickCallback movieClickCallback) {
        this.movieClickCallback = movieClickCallback;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
