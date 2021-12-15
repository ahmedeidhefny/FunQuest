package com.helloWorldTech.funQuest.data.remote.modelResponse;

import com.google.gson.annotations.SerializedName;

public class CreateChallengeApiResponse {

    @SerializedName("status")
    private Boolean status;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public CreateChallengeApiResponse(Boolean status, int code, String message, Data data) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public static class Data {
        @SerializedName("validation_errors")
        private ValidationErrors validationErrors;

        public Data(ValidationErrors validationErrors) {
            this.validationErrors = validationErrors;
        }

        public ValidationErrors getValidationErrors() {
            return validationErrors;
        }

        public void setValidationErrors(ValidationErrors validationErrors) {
            this.validationErrors = validationErrors;
        }
    }

    public static class ValidationErrors {
        @SerializedName("email")
        private String email;
        @SerializedName("city_id")
        private String cityId;
        @SerializedName("place_id")
        private String placeId;
        @SerializedName("team_count")
        private String teamCount;
        @SerializedName("date_time")
        private String dateTime;

        public ValidationErrors(String email, String cityId, String placeId, String teamCount, String dateTime) {
            this.email = email;
            this.cityId = cityId;
            this.placeId = placeId;
            this.teamCount = teamCount;
            this.dateTime = dateTime;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCityId() {
            return cityId;
        }

        public void setCityId(String cityId) {
            this.cityId = cityId;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public String getTeamCount() {
            return teamCount;
        }

        public void setTeamCount(String teamCount) {
            this.teamCount = teamCount;
        }

        public String getDateTime() {
            return dateTime;
        }

        public void setDateTime(String dateTime) {
            this.dateTime = dateTime;
        }
    }
}
