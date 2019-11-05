package bases.commonmodel;


import java.io.Serializable;

import utilities.UploadImageTypes;

/**
 * Created by abdalla_maged On Dec,2018
 */
public class UploadImageType implements Serializable {


    private String imageId;
    private String imageUrl;
    private UploadImageTypes uploadImageType;


    private String imageCaption;
    private String imageLocation;
    private boolean draft;


    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public UploadImageTypes getUploadImageType() {
        return uploadImageType;
    }

    public void setUploadImageType(UploadImageTypes uploadImageType) {
        this.uploadImageType = uploadImageType;
    }

    public String getImageCaption() {
        return imageCaption;
    }

    public void setImageCaption(String imageCaption) {
        this.imageCaption = imageCaption;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public boolean isDraft() {
        return draft;
    }

    public void setDraft(boolean draft) {
        this.draft = draft;
    }

}
