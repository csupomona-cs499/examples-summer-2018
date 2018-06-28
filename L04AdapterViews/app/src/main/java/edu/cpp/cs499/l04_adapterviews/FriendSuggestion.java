package edu.cpp.cs499.l04_adapterviews;

public class FriendSuggestion {

    private String name;
    private int numMutualFriends;
    private int profilePhotoResId;

    public FriendSuggestion() {

    }

    public FriendSuggestion(String name, int numMutualFriends, int profilePhotoResId) {
        this.name = name;
        this.numMutualFriends = numMutualFriends;
        this.profilePhotoResId = profilePhotoResId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumMutualFriends() {
        return numMutualFriends;
    }

    public void setNumMutualFriends(int numMutualFriends) {
        this.numMutualFriends = numMutualFriends;
    }

    public int getProfilePhotoResId() {
        return profilePhotoResId;
    }

    public void setProfilePhotoResId(int profilePhotoResId) {
        this.profilePhotoResId = profilePhotoResId;
    }
}
