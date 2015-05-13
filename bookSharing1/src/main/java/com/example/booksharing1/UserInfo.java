package com.example.booksharing1;

import android.location.Address;

import com.example.booksharing1.JSON.User;

import java.util.List;
import java.util.Set;


public class UserInfo {
    private static final UserInfo uInfo = new UserInfo();
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

    private String workLocation;


    // Private constructor prevents instantiation from other classes
    private UserInfo() {}

    public static UserInfo getInstance() {
        return uInfo;
    }
    public void copy(User u) {
        id = u.getId();
        fbId = u.getFbId();
        name = u.getName();
        email = u.getEmail();
        profileImageUrl = u.getProfileImageUrl();
        lastModifiedDate = u.getLastModifiedDate();
        addresses = u.getAddresses();
        phone = u.getPhone();
        createdDate = u.getCreatedDate();
        goodreadsAuthStatus = u.getGoodreadsAuthStatus();
        goodreadsId = u.getGoodreadsId();
        goodreadsAccessToken = u.getGoodreadsAccessToken();
        goodreadsAccessTokenSecret = u.getGoodreadsAccessTokenSecret();
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getGoodreadsAccessTokenSecret() {
        return goodreadsAccessTokenSecret;
    }

    public void setGoodreadsAccessTokenSecret(String goodreadsAccessTokenSecret) {
        this.goodreadsAccessTokenSecret = goodreadsAccessTokenSecret;
    }

    public String getGoodreadsId() {
        return goodreadsId;
    }

    public void setGoodreadsId(String goodreadsId) {
        this.goodreadsId = goodreadsId;
    }

    public String getGoodreadsAccessToken() {
        return goodreadsAccessToken;
    }

    public void setGoodreadsAccessToken(String goodreadsAccessToken) {
        this.goodreadsAccessToken = goodreadsAccessToken;
    }

    public String getFbId() {
        return fbId;
    }

    public void setFbId(String fbId) {
        this.fbId = fbId;
    }

    public long getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(long lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public long getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(long createdDate) {
        this.createdDate = createdDate;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGoodreadsAuthStatus() {
        return goodreadsAuthStatus;
    }

    public void setGoodreadsAuthStatus(String goodreadsAuthStatus) {
        this.goodreadsAuthStatus = goodreadsAuthStatus;
    }
}
