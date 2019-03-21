package neel.com.mocksocialapp.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

@Entity(tableName = "user_tbl")
public class User implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    private int user_id;

    @SerializedName("_id")
    @Expose
    private String id;
    @SerializedName("about")
    @Expose
    private String about;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("age")
    @Expose
    private String age;
    @SerializedName("email")
    @Expose
    private String email;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("picture")
    @Expose
    private String picture;

    private String first_name;
    private String last_name;
    @Ignore
    private ArrayList<Friend> friends;

    private ArrayList<String> gallery_urls;

    @Ignore
    public User() {
    }


    @Ignore
    public User(String id, String about, String address, String age, String email, String phone, String picture, String first_name,
                String last_name, ArrayList<Friend> friends, ArrayList<String> gallery_urls) {
        this.id = id;
        this.about = about;
        this.address = address;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.picture = picture;
        this.first_name = first_name;
        this.last_name = last_name;
        this.friends = friends;
        this.gallery_urls = gallery_urls;
    }

    public User(int user_id, String id, String about, String address, String age, String email, String phone, String picture, String first_name, String last_name,
                ArrayList<String> gallery_urls) {
        this.user_id = user_id;
        this.id = id;
        this.about = about;
        this.address = address;
        this.age = age;
        this.email = email;
        this.phone = phone;
        this.picture = picture;
        this.first_name = first_name;
        this.last_name = last_name;
        this.gallery_urls = gallery_urls;
    }


    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public ArrayList<Friend> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<Friend> friends) {
        this.friends = friends;
    }


    public ArrayList<String> getGallery_urls() {
        return gallery_urls;
    }

    public void setGallery_urls(ArrayList<String> gallery_urls) {
        this.gallery_urls = gallery_urls;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(first_name);
        parcel.writeString(last_name);
        parcel.writeString(about);
        parcel.writeString(address);
        parcel.writeString(picture);
        parcel.writeList(gallery_urls);


    }


    private User(Parcel in){
        first_name=in.readString();
        last_name=in.readString();
        about=in.readString();
        address=in.readString();
        picture=in.readString();
        gallery_urls = in.readArrayList(String.class.getClassLoader());

    }

    public static final Creator<User>CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };


    public static class Friend {
        public String id;
        public String name;

        public Friend(String id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
