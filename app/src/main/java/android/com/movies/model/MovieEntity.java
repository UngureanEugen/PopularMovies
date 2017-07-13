package android.com.movies.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("ALL") @Entity(tableName = "movies")
public class MovieEntity implements Parcelable {

  @SerializedName("vote_count")
  @Expose
  public Integer voteCount;
  @PrimaryKey
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

  public MovieEntity() {
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

  @Override public int describeContents() {
    return 0;
  }

  @Override public void writeToParcel(Parcel dest, int flags) {

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
  }
}
