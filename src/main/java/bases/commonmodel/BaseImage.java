package bases.commonmodel;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class BaseImage implements Parcelable {

    public static final Creator<BaseImage> CREATOR = new Creator<BaseImage>() {
        @Override
        public BaseImage createFromParcel(Parcel source) {
            return new BaseImage(source);
        }

        @Override
        public BaseImage[] newArray(int size) {
            return new BaseImage[size];
        }
    };
    @SerializedName("caption")
    @Expose
    public String caption;

    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("location")
    @Expose
    public String location;
    @SerializedName("thumbnail_url")
    @Expose
    public String thumbnailUrl;
    @SerializedName("url")
    @Expose
    public String url;

    @SerializedName("album_name")
    @Expose
    public String albumName;


    @SerializedName("is_rated")
    @Expose
    public Boolean isRated;
    @SerializedName("is_cart")
    @Expose
    public Boolean isCart;


    @SerializedName("id")
    @Expose
    public int id;
    @SerializedName("photographer")
    @Expose
    public Photographer photographer;
    @SerializedName("is_saved")
    @Expose
    public Boolean isSaved;
    @SerializedName("is_liked")
    @Expose
    public Boolean isLiked;
    @SerializedName("tags")
    @Expose
    public List<Tag> tags;


    @Expose
    public String filters;


    @SerializedName("comments_count")
    @Expose
    public Integer commentsCount;
    @SerializedName("saves_count")
    @Expose
    public Integer savesCount;
    @SerializedName("likes_count")
    @Expose
    public Integer likesCount;
    @SerializedName("rate")
    @Expose
    public float rate = 0;

    //this is custom flag for developing
    public boolean isImageDeleted;

    public BaseImage() {

    }

    protected BaseImage(Parcel in) {
        this.caption = in.readString();
        this.updatedAt = in.readString();
        this.createdAt = in.readString();
        this.location = in.readString();
        this.thumbnailUrl = in.readString();
        this.url = in.readString();
        this.albumName = in.readString();
        this.isRated = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isCart = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.id = in.readInt();
        this.photographer = in.readParcelable(Photographer.class.getClassLoader());
        this.isSaved = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.isLiked = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.tags = in.createTypedArrayList(Tag.CREATOR);
        this.filters = in.readString();
        this.commentsCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.savesCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.likesCount = (Integer) in.readValue(Integer.class.getClassLoader());
        this.rate = in.readFloat();
        this.isImageDeleted = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.caption);
        dest.writeString(this.updatedAt);
        dest.writeString(this.createdAt);
        dest.writeString(this.location);
        dest.writeString(this.thumbnailUrl);
        dest.writeString(this.url);
        dest.writeString(this.albumName);
        dest.writeValue(this.isRated);
        dest.writeValue(this.isCart);
        dest.writeInt(this.id);
        dest.writeParcelable(this.photographer, flags);
        dest.writeValue(this.isSaved);
        dest.writeValue(this.isLiked);
        dest.writeTypedList(this.tags);
        dest.writeString(this.filters);
        dest.writeValue(this.commentsCount);
        dest.writeValue(this.savesCount);
        dest.writeValue(this.likesCount);
        dest.writeFloat(this.rate);
        dest.writeByte(this.isImageDeleted ? (byte) 1 : (byte) 0);
    }
}
