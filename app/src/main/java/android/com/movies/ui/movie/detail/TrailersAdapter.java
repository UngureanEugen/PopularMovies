package android.com.movies.ui.movie.detail;

import android.com.movies.R;
import android.com.movies.model.Video;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class TrailersAdapter extends RecyclerView.Adapter<TrailersAdapter.TrailerViewHolder> {
  private final List<Video> trailers = new ArrayList<>(0);
  private final OnTrailerClickListener onTrailerClickListener;

  public TrailersAdapter(OnTrailerClickListener onTrailerClickListener) {
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
    return trailers.size();
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
      onTrailerClickListener.onClick(trailers.get(getAdapterPosition()));
    }
  }

  public void setTrailers(List<Video> items) {
    trailers.clear();
    trailers.addAll(items);
    notifyDataSetChanged();
  }

  public interface OnTrailerClickListener {
    void onClick(Video trailer);
  }
}
