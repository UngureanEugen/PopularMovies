package android.com.movies.util;

import android.com.movies.BuildConfig;
import android.support.annotation.NonNull;
import java.io.IOException;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class RequestInterceptor implements Interceptor {
  @Override
  public Response intercept(@NonNull Chain chain) throws IOException {
    Request request = chain.request();
    HttpUrl httpUrl = request.url();
    HttpUrl urlWithKey = httpUrl.newBuilder()
        .addQueryParameter("api_key", BuildConfig.API_KEY)
        .build();
    Request requestWithKey = request.newBuilder().url(urlWithKey).build();
    return chain.proceed(requestWithKey);
  }
}
