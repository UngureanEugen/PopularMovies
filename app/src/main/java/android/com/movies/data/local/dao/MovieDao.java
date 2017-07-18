/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package android.com.movies.data.local.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.com.movies.model.MovieEntity;
import android.com.movies.model.Review;
import android.com.movies.model.Video;
import android.support.annotation.VisibleForTesting;
import java.util.List;

@Dao
public interface MovieDao {

  @Query("SELECT * FROM movies ORDER BY popularity DESC")
  LiveData<List<MovieEntity>> loadMostPopularMovies();

  @Query("SELECT * FROM movies ORDER BY voteAverage DESC")
  LiveData<List<MovieEntity>> loadTopRatedMovies();

  @VisibleForTesting
  @Query("SELECT * FROM movies")
  List<MovieEntity> loadMovies();

  @VisibleForTesting
  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertMovies(List<MovieEntity> movies);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertVideos(List<Video> videos);

  @Query("SELECT * FROM videos WHERE movie_id == :movieId")
  LiveData<List<Video>> loadVideos(int movieId);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  void insertReviews(List<Review> reviews);

  @Query("SELECT * FROM reviews WHERE movie_id = :movieId")
  LiveData<List<Review>> loadReviews(int movieId);
}