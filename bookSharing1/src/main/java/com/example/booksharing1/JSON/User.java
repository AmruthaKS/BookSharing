package com.example.booksharing1.JSON;

import android.location.Address;


import java.io.Serializable;
import java.util.List;
import java.util.Set;



public class User implements Serializable {


    private List<Address> addresses;
    private long createdDate;
    private String email;
    private Set<String> favorites;

    private String fbId;
    private String goodreadsAccessToken;
    private String goodreadsAccessTokenSecret;
    private String goodreadsAuthStatus;
    private String goodreadsId;

    private String id;

    private long lastModifiedDate;
    private String name;

    private Long nodeId;
    private String phone;

    private String profileImageUrl;

    private String workDesignation;
    private String googleId;

    private String workLocation;
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;
    public String getGoogleId() {
        return googleId;
    }

    public void setGoogleId(String googleId) {
        this.googleId = googleId;
    }


    public String getGoodReadsSynchStatus() {
        return goodReadsSynchStatus;
    }

    public void setGoodReadsSynchStatus(String goodReadsSynchStatus) {
        this.goodReadsSynchStatus = goodReadsSynchStatus;
    }

    public long getLastGoodreadsSychDate() {
        return lastGoodreadsSychDate;
    }

    public void setLastGoodreadsSychDate(long lastGoodreadsSychDate) {
        this.lastGoodreadsSychDate = lastGoodreadsSychDate;
    }

    private String goodReadsSynchStatus;
    private long lastGoodreadsSychDate;

    public User() {

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public User(String name, String email , String fbId) {
        this.name = name;
        this.email = email;
        this.fbId = fbId;
    }
    public User(String id) {
        this.id = id;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public long getCreatedDate() {
        return createdDate;
    }
    public String getEmail() {
        return email;
    }
    public Set<String> getFavorites() {
        return favorites;
    }
    public String getFbId() {
        return fbId;
    }

    public String getGoodreadsAccessToken() {
        return goodreadsAccessToken;
    }

    public String getGoodreadsAccessTokenSecret() {
        return goodreadsAccessTokenSecret;
    }

    public String getGoodreadsAuthStatus() {
        return goodreadsAuthStatus;
    }

    public String getGoodreadsId() {
        return goodreadsId;
    }

    public String getId() {
        return id;
    }

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public String getName() {
        return name;
    }

    public Long getNodeId() {
        return nodeId;
    }

    public String getPhone() {
        return phone;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getWorkDesignation() {
        return workDesignation;
    }

    public String getWorkLocation() {
        return workLocation;
    }



    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFavorites(Set<String> favourites) {
        this.favorites = favourites;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public void setGoodreadsAccessToken(String goodreadsAccessToken) {
        this.goodreadsAccessToken = goodreadsAccessToken;
    }

    public void setGoodreadsAccessTokenSecret(String goodreadsAccessTokenSecret) {
        this.goodreadsAccessTokenSecret = goodreadsAccessTokenSecret;
    }

    public void setGoodreadsAuthStatus(String goodreadsAuthStatus) {
        this.goodreadsAuthStatus = goodreadsAuthStatus;
    }

    public void setGoodreadsId(String goodreadsId) {
        this.goodreadsId = goodreadsId;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLastModifiedDate(long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNodeId(Long nodeId) {
        this.nodeId = nodeId;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public void setWorkDesignation(String workDesignation) {
        this.workDesignation = workDesignation;
    }

    public void setWorkLocation(String workLocation) {
        this.workLocation = workLocation;
    }

    @Override
    public String toString() {
        return "User{" +
                "nodeId=" + nodeId +
                ", id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", addresses=" + addresses +
                ", phone='" + phone + '\'' +
                ", createdDate=" + createdDate +
                '}';
    }
}
































