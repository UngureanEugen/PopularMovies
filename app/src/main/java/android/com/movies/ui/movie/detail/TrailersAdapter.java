package android.com.movies.ui.movie.detail;

import android.com.movies.R;
import android.com.movies.model.Video;
import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {
  private Cursor trailersCursor;
  private final OnTrailerClickListener onTrailerClickListener;

  public TrailersAdapter( OnTrailerClickListener onTrailerClickListener) {
    this.onTrailerClickListener = onTrailerClickListener;
  }

  @Override
  public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.trailer_list_item, parent, false);
    return new TrailerViewHolder(view);
  }

  @Override public void onBindViewHolder(TrailerViewHolder holder, int position) {
    holder.bind(position);
  }

  @Override public int getItemCount() {
    return (trailersCursor != null) ? trailersCursor.getCount() : 0;
  }

  public void swapTrailersCursor(Cursor data) {
    if (trailersCursor != null) {
      trailersCursor.close();
    }
    trailersCursor = data;
    notifyDataSetChanged();
  }

  public Video getTrailer(int position) {
    if (!trailersCursor.moveToPosition(position)) {
      throw new IllegalArgumentException("Invalid position");
    }
    return new Video(trailersCursor);
  }

  public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView tvTrailer;
    private final Context context;

    public TrailerViewHolder(View itemView) {
      super(itemView);
      context = itemView.getContext();
      tvTrailer = (TextView) itemView.findViewById(R.id.tv_trailer);
      itemView.setOnClickListener(this);
    }

    public void bind(int position) {
      tvTrailer.setText(String.format(context.getString(R.string.trailer), position + 1));
    }

    @Override public void onClick(View v) {
      onTrailerClickListener.onClick(getTrailer(getAdapterPosition()));
    }
  }

  public interface OnTrailerClickListener {
    void onClick(Video trailer);
  }
}
