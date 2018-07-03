package edu.cpp.cs499.l06_flickr_app;

public class RecentPhotoResponse {

    private PhotosData photos;
    private String stat;

    public PhotosData getPhotos() {
        return photos;
    }

    public void setPhotos(PhotosData photos) {
        this.photos = photos;
    }

    public String getStat() {
        return stat;
    }

    public void setStat(String stat) {
        this.stat = stat;
    }
}
