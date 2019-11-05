package bases.commonmodel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class Campaign {
    @SerializedName("end_date")
    @Expose
    public String endDate;
    @SerializedName("image_cover")
    @Expose
    public String imageCover;
    @SerializedName("descrption_en")
    @Expose
    public String descrptionEn;
    @SerializedName("updated_at")
    @Expose
    public String updatedAt;
    @SerializedName("number_images")
    @Expose
    public int numberImages;
    @SerializedName("publish")
    @Expose
    public String publish;
    @SerializedName("created_at")
    @Expose
    public String createdAt;
    @SerializedName("title_en")
    @Expose
    public String titleEn;
    @SerializedName("prize")
    @Expose
    public String prize;
    @SerializedName("start_date")
    @Expose
    public String startDate;
    @SerializedName("status")
    @Expose
    public int status;
    @SerializedName("id")
    @Expose
    public Integer id;
    @SerializedName("business")
    @Expose
    public Business business;
    @SerializedName("days_left")
    @Expose
    public Integer daysLeft;
    @SerializedName("tags")
    @Expose
    public List<Tag> tags = null;
    @SerializedName("no_winners")
    @Expose
    public String noWinners;
    @SerializedName("is_joined")
    public boolean isJoined;
    @Expose
    @SerializedName("photos_count")
    public int photosCount;


}
