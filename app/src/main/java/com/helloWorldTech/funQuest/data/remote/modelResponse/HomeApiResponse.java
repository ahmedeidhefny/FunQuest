package com.helloWorldTech.funQuest.data.remote.modelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HomeApiResponse {

    @SerializedName("status")
    private Boolean status;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public HomeApiResponse(Boolean status, int code, String message, Data data) {
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
        @SerializedName("cities")
        private List<Cities> cities;
        @SerializedName("most_cities")
        private List<MostCities> mostCities;
        @SerializedName("game")
        private Game game;

        public Data(List<Cities> cities, List<MostCities> mostCities, Game game) {
            this.cities = cities;
            this.mostCities = mostCities;
            this.game = game;
        }

        public List<Cities> getCities() {
            return cities;
        }

        public void setCities(List<Cities> cities) {
            this.cities = cities;
        }

        public List<MostCities> getMostCities() {
            return mostCities;
        }

        public void setMostCities(List<MostCities> mostCities) {
            this.mostCities = mostCities;
        }

        public Game getGame() {
            return game;
        }

        public void setGame(Game game) {
            this.game = game;
        }

    }
    public static class Game {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("image")
        private String image;
        @SerializedName("start_date_time")
        private String startDateTime;
        @SerializedName("duration")
        private int duration;
        @SerializedName("city_name")
        private String cityName;
        @SerializedName("place_name")
        private String placeName;

        public Game(int id, String name, String image, String startDateTime, int duration, String cityName, String placeName) {
            this.id = id;
            this.name = name;
            this.image = image;
            this.startDateTime = startDateTime;
            this.duration = duration;
            this.cityName = cityName;
            this.placeName = placeName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(String startDateTime) {
            this.startDateTime = startDateTime;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getPlaceName() {
            return placeName;
        }

        public void setPlaceName(String placeName) {
            this.placeName = placeName;
        }
    }

    public static class Cities {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("image")
        private String image;
        @SerializedName("places_count")
        private int placesCount;

        public Cities(int id, String name, String image, int placesCount) {
            this.id = id;
            this.name = name;
            this.image = image;
            this.placesCount = placesCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getPlacesCount() {
            return placesCount;
        }

        public void setPlacesCount(int placesCount) {
            this.placesCount = placesCount;
        }
    }

    public static class MostCities {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("image")
        private String image;
        @SerializedName("places_count")
        private int placesCount;

        public MostCities(int id, String name, String image, int placesCount) {
            this.id = id;
            this.name = name;
            this.image = image;
            this.placesCount = placesCount;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getPlacesCount() {
            return placesCount;
        }

        public void setPlacesCount(int placesCount) {
            this.placesCount = placesCount;
        }
    }
}
