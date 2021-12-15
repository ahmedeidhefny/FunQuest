package com.helloWorldTech.funQuest.data.local.entity;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * @author Ahmed Eid Hefny
 * @date 26/11/2021
 * <p>
 * ahmedeid2026@gmail.com
 **/
@Entity
public  class UserEntity {
    @Expose
    @PrimaryKey
    @SerializedName("id")
    public int id;
    @Expose
    @SerializedName("data")
    @Embedded(prefix = "data_")
    private DataEntity data;
    @Expose
    @SerializedName("message")
    private String message;
    @Expose
    @SerializedName("code")
    private int code;
    @Expose
    @SerializedName("status")
    private boolean status;

    public UserEntity(int id, String message, int code, boolean status, DataEntity data) {
        this.id = id;
        this.data = data;
        this.message = message;
        this.code = code;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public DataEntity getData() {
        return data;
    }

    public void setData(DataEntity data) {
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

    public static class DataEntity {
        @Expose
        @SerializedName("token")
        private String token;
        @Expose
        @SerializedName("last_login")
        private String lastLogin;
        @Expose
        @SerializedName("user")
        @Embedded(prefix = "user_")
        private User user;
        @Expose
        @SerializedName("validation_errors")
        @Embedded(prefix = "validationErrors_")
        private ValidationErrors validationErrors ;

        public DataEntity() {
        }

        public DataEntity(String token, User user, ValidationErrors validationErrors) {
            this.token = token;
            this.user = user;
            this.validationErrors = validationErrors;
        }

        public String getLastLogin() {
            return lastLogin;
        }

        public void setLastLogin(String lastLogin) {
            this.lastLogin = lastLogin;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public ValidationErrors getValidationErrors() {
            return validationErrors;
        }

        public void setValidationErrors(ValidationErrors validationErrors) {
            this.validationErrors = validationErrors;
        }
    }

    public static class User {

        @Expose
        @SerializedName("age_range")
        private String ageRange;
        @Expose
        @SerializedName("email")
        private String email;
        @Expose
        @SerializedName("image")
        private String image;
        @Expose
        @SerializedName("name")
        private String name;
        @Expose
        @SerializedName("id")
        private int id;

        public User() {
        }

        public User(int id, String ageRange, String email, String image, String name) {
            this.ageRange = ageRange;
            this.email = email;
            this.image = image;
            this.name = name;
            this.id = id;
        }

        public String getAgeRange() {
            return ageRange;
        }

        public void setAgeRange(String ageRange) {
            this.ageRange = ageRange;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public static class ValidationErrors {
        @Expose
        @SerializedName("password")
        private String password;
        @Expose
        @SerializedName("mobile")
        private String mobile;

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }
    }
}
