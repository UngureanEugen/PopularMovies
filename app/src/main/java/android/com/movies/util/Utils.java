package android.com.movies.util;

import android.content.Context;

public final class Utils {
  private Utils() {
  }

  public static float dpToPixel(Context ctx, float dp) {
    // Get the screen's density scale
    final float scale = ctx.getResources().getDisplayMetrics().density;
    // Convert the dps to pixels, based on density scale
    return dp * scale + 0.5f;
  }
}
