package android.com.movies.ui.movie.detail;

import android.com.movies.R;
import android.com.movies.model.Review;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewViewHolder> {
  private List<Review> reviews = new ArrayList<>(0);

  @Override
  public ReviewViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    LayoutInflater inflater = LayoutInflater.from(parent.getContext());
    View view = inflater.inflate(R.layout.review_list_item, parent, false);
    return new ReviewViewHolder(view);
  }

  @Override public void onBindViewHolder(ReviewViewHolder holder, int position) {
    holder.bind(position);
  }

  @Override public int getItemCount() {
    return reviews.size();
  }

  public class ReviewViewHolder extends RecyclerView.ViewHolder {
    private TextView tvReview;

    public ReviewViewHolder(View itemView) {
      super(itemView);
      tvReview = (TextView) itemView.findViewById(R.id.tv_review);
    }

    public void bind(int position) {
      tvReview.setText(reviews.get(position).content);
    }
  }

  public void setReviews(List<Review> items) {
    reviews.clear();
    reviews.addAll(items);
    notifyDataSetChanged();
  }
}
