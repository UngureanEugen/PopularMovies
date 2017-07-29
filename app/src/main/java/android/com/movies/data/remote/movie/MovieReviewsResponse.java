package android.com.movies.data.remote.movie;

import android.com.movies.model.Review;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import java.util.List;

public class MovieReviewsResponse {
  @SerializedName("id")
  @Expose
  public Integer movieId;
  @SerializedName("page")
  @Expose
  public Integer page;
  @SerializedName("results")
  @Expose
  public List<Review> reviews;
  @SerializedName("total_pages")
  @Expose
  public Integer totalPages;
  @SerializedName("total_results")
  @Expose
  public Integer totalResults;
}
