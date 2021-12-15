package com.helloWorldTech.funQuest.data.local.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/

@Entity
public class AppDataResponse {

    @Expose
    @PrimaryKey
    @SerializedName("id")
    public int id;
    @Expose
    @SerializedName("data")
    @Embedded(prefix = "data_")
    private Data data;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("code")
    private int code;
    @Expose
    @SerializedName("status")
    private boolean status;


    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public static class Data {
        @Expose
        @SerializedName("splash")
        @Embedded(prefix = "splash_")
        private Splash splash;
        @Expose
        @SerializedName("home")
        @Embedded(prefix = "home_")
        private Home home;
        @Expose
        @SerializedName("age_range")
        private List<AgeRange> ageRange;

        public Data() {

        }

        public Data(Splash splash, Home home, List<AgeRange> ageRange) {
            this.splash = splash;
            this.home = home;
            this.ageRange = ageRange;
        }

        public Splash getSplash() {
            return splash;
        }

        public void setSplash(Splash splash) {
            this.splash = splash;
        }

        public Home getHome() {
            return home;
        }

        public void setHome(Home home) {
            this.home = home;
        }

        public List<AgeRange> getAgeRange() {
            return ageRange;
        }

        public void setAgeRange(List<AgeRange> ageRange) {
            this.ageRange = ageRange;
        }
    }

    public static class AgeRange {
        @Expose
        @SerializedName("key")
        private String key;

        @Expose
        @SerializedName("value")
        private String value;

        public AgeRange() {
        }

        public AgeRange(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public static class Splash {
        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("description")
        private String description;

        public Splash() {
        }

        public Splash(String key, String value) {
            this.title = key;
            this.description = value;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

    public static class Home {
        @Expose
        @SerializedName("title")
        private String title;

        @Expose
        @SerializedName("description")
        private String description;

        public Home() {
        }

        public Home(String key, String value) {
            this.title = key;
            this.description = value;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }

}
