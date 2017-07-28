package android.com.movies.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import static android.com.movies.data.DatabaseContract.MovieColumns;
import static android.com.movies.data.DatabaseContract.getColumnDouble;
import static android.com.movies.data.DatabaseContract.getColumnInt;
import static android.com.movies.data.DatabaseContract.getColumnString;

@SuppressWarnings("ALL")
public class MovieEntity implements Parcelable {

  @SerializedName("vote_count")
  @Expose
  public Integer voteCount;
  @SerializedName("id")
  @Expose
  public Integer id;
  @SerializedName("video")
  @Expose
  public Boolean video;
  @SerializedName("vote_average")
  @Expose
  public Double voteAverage;
  @SerializedName("title")
  @Expose
  public String title;
  @SerializedName("popularity")
  @Expose
  public Double popularity;
  @SerializedName("poster_path")
  @Expose
  public String posterPath;
  @SerializedName("original_language")
  @Expose
  public String originalLanguage;
  @SerializedName("original_title")
  @Expose
  public String originalTitle;
  @SerializedName("backdrop_path")
  @Expose
  public String backdropPath;
  @SerializedName("adult")
  @Expose
  public Boolean adult;
  @SerializedName("overview")
  @Expose
  public String overview;
  @SerializedName("release_date")
  @Expose
  public String releaseDate;
  public Boolean isFavorite;

  public MovieEntity() {
  }

  public MovieEntity(Cursor cursor) {
    this.id = getColumnInt(cursor, MovieColumns._ID);
    this.title = getColumnString(cursor, MovieColumns.TITLE);
    this.posterPath = getColumnString(cursor, MovieColumns.POSTER_PATH);
    this.adult = getColumnInt(cursor, MovieColumns.IS_ADULT) == 1;
    this.backdropPath = getColumnString(cursor, MovieColumns.BACKDROP_PATH);
    this.originalLanguage = getColumnString(cursor, MovieColumns.ORIGINAL_LANGUAGE);
    this.overview = getColumnString(cursor, MovieColumns.OVERVIEW);
    this.popularity = getColumnDouble(cursor, MovieColumns.POPULARITY);
    this.originalTitle = getColumnString(cursor, MovieColumns.ORIGINAL_TITLE);
    this.releaseDate = getColumnString(cursor, MovieColumns.RELEASE_DATE);
    this.video = getColumnInt(cursor, MovieColumns.HAS_VIDEO) == 1;
    this.voteAverage = getColumnDouble(cursor, MovieColumns.VOTE_AVERAGE);
    this.voteCount = getColumnInt(cursor, MovieColumns.VOTE_COUNT);
    this.isFavorite = getColumnInt(cursor, MovieColumns.IS_FAVORITE) == 1;
  }

  protected MovieEntity(Parcel in) {
    if (in.readByte() == 0) {
      voteCount = null;
    } else {
      voteCount = in.readInt();
    }
    if (in.readByte() == 0) {
      id = null;
    } else {
      id = in.readInt();
    }
    byte tmpVideo = in.readByte();
    video = tmpVideo == 0 ? null : tmpVideo == 1;
    if (in.readByte() == 0) {
      voteAverage = null;
    } else {
      voteAverage = in.readDouble();
    }
    title = in.readString();
    if (in.readByte() == 0) {
      popularity = null;
    } else {
      popularity = in.readDouble();
    }
    posterPath = in.readString();
    originalLanguage = in.readString();
    originalTitle = in.readString();
    backdropPath = in.readString();
    byte tmpAdult = in.readByte();
    adult = tmpAdult == 0 ? null : tmpAdult == 1;
    overview = in.readString();
    releaseDate = in.readString();
    byte tmpIsFavorite = in.readByte();
    isFavorite = tmpIsFavorite == 0 ? null : tmpIsFavorite == 1;
  }

  @Override
  public void writeToParcel(Parcel dest, int flags) {
    if (voteCount == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeInt(voteCount);
    }
    if (id == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeInt(id);
    }
    dest.writeByte((byte) (video == null ? 0 : video ? 1 : 2));
    if (voteAverage == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeDouble(voteAverage);
    }
    dest.writeString(title);
    if (popularity == null) {
      dest.writeByte((byte) 0);
    } else {
      dest.writeByte((byte) 1);
      dest.writeDouble(popularity);
    }
    dest.writeString(posterPath);
    dest.writeString(originalLanguage);
    dest.writeString(originalTitle);
    dest.writeString(backdropPath);
    dest.writeByte((byte) (adult == null ? 0 : adult ? 1 : 2));
    dest.writeString(overview);
    dest.writeString(releaseDate);
    dest.writeByte((byte) (isFavorite == null ? 0 : isFavorite ? 1 : 2));
  }

  @Override
  public int describeContents() {
    return 0;
  }

  public static final Creator<MovieEntity> CREATOR = new Creator<MovieEntity>() {
    @Override
    public MovieEntity createFromParcel(Parcel in) {
      return new MovieEntity(in);
    }

    @Override
    public MovieEntity[] newArray(int size) {
      return new MovieEntity[size];
    }
  };

  public static ContentValues convert(MovieEntity entity) {
    ContentValues movieContent = new ContentValues();
    movieContent.put(MovieColumns._ID, entity.id);
    movieContent.put(MovieColumns.TITLE, entity.title);
    movieContent.put(MovieColumns.POSTER_PATH, entity.posterPath);
    movieContent.put(MovieColumns.IS_ADULT, entity.adult);
    movieContent.put(MovieColumns.BACKDROP_PATH, entity.backdropPath);
    movieContent.put(MovieColumns.ORIGINAL_LANGUAGE, entity.originalLanguage);
    movieContent.put(MovieColumns.OVERVIEW, entity.overview);
    movieContent.put(MovieColumns.POPULARITY, entity.popularity);
    movieContent.put(MovieColumns.ORIGINAL_TITLE, entity.originalTitle);
    movieContent.put(MovieColumns.RELEASE_DATE, entity.releaseDate);
    movieContent.put(MovieColumns.HAS_VIDEO, entity.video);
    movieContent.put(MovieColumns.VOTE_AVERAGE, entity.voteAverage);
    movieContent.put(MovieColumns.VOTE_COUNT, entity.voteCount);
    movieContent.put(MovieColumns.IS_FAVORITE, entity.isFavorite);
    return movieContent;
  }
}
