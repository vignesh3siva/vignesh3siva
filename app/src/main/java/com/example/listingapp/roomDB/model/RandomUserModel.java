package com.example.listingapp.roomDB.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "randomusers")
public class RandomUserModel {
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    private int id;

    //login
    @SerializedName("uuid")
    @Expose
    @ColumnInfo(name = "uuid")
    private String uuid;
    @SerializedName("username")
    @ColumnInfo(name = "username")
    @Expose
    private String username;

    //id
    @SerializedName("name")
    @Expose
    @ColumnInfo(name = "name")
    private String name;

    //name
    @SerializedName("title")
    @Expose
    @ColumnInfo(name = "title")
    private String title;
    @SerializedName("first")
    @Expose
    @ColumnInfo(name = "first")
    private String first;
    @SerializedName("last")
    @Expose
    @ColumnInfo(name = "last")
    private String last;

    @SerializedName("gender")
    @Expose
    @ColumnInfo(name = "gender")
    private String gender;

    @SerializedName("email")
    @Expose
    @ColumnInfo(name = "email")
    private String email;

    //location
    @SerializedName("city")
    @Expose
    @ColumnInfo(name = "city")
    private String city;
    @SerializedName("state")
    @Expose
    @ColumnInfo(name = "state")
    private String state;
    @SerializedName("country")
    @Expose
    @ColumnInfo(name = "country")
    private String country;
    @SerializedName("postcode")
    @Expose
    @ColumnInfo(name = "postcode")
    private String postcode;

    //dob
    @SerializedName("date")
    @Expose
    @ColumnInfo(name = "date")
    private String date;
    @SerializedName("age")
    @Expose
    @ColumnInfo(name = "age")
    private String age;

    @SerializedName("phone")
    @Expose
    @ColumnInfo(name = "phone")
    private String phone;

    //picture
    @SerializedName("large")
    @Expose
    @ColumnInfo(name = "large")
    private String large;
    @SerializedName("medium")
    @Expose
    @ColumnInfo(name = "medium")
    private String medium;
    @SerializedName("thumbnail")
    @Expose
    @ColumnInfo(name = "thumbnail")
    private String thumbnail;

    //coordinates
    @SerializedName("latitude")
    @Expose
    @ColumnInfo(name = "latitude")
    private String latitude;
    @SerializedName("longitude")
    @Expose
    @ColumnInfo(name = "longitude")
    private String longitude;

    //for displaying data
    @ColumnInfo(name = "displayno")
    private int displayno;

    public int getDisplayno() {
        return displayno;
    }

    public void setDisplayno(int displayno) {
        this.displayno = displayno;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getLast() {
        return last;
    }

    public void setLast(String last) {
        this.last = last;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLarge() {
        return large;
    }

    public void setLarge(String large) {
        this.large = large;
    }

    public String getMedium() {
        return medium;
    }

    public void setMedium(String medium) {
        this.medium = medium;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}


