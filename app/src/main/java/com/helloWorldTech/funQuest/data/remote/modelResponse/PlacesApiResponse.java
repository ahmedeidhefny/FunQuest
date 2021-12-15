package com.helloWorldTech.funQuest.data.remote.modelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PlacesApiResponse {

    @SerializedName("status")
    private Boolean status;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public PlacesApiResponse(Boolean status, int code, String message, Data data) {
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
        @SerializedName("places")
        private List<Places> places;
        @SerializedName("most_places")
        private List<MostPlaces> mostPlaces;
        @SerializedName("city")
        private City city;

        public Data(List<Places> places, List<MostPlaces> mostPlaces, City city) {
            this.places = places;
            this.mostPlaces = mostPlaces;
            this.city = city;
        }

        public List<Places> getPlaces() {
            return places;
        }

        public void setPlaces(List<Places> places) {
            this.places = places;
        }

        public List<MostPlaces> getMostPlaces() {
            return mostPlaces;
        }

        public void setMostPlaces(List<MostPlaces> mostPlaces) {
            this.mostPlaces = mostPlaces;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

    }
    public static class City {
        @SerializedName("name")
        private String name;

        public City(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class Places {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("image")
        private String image;
        @SerializedName("games_count")
        private int gamesCount;

        public Places(int id, String name, String image, int gamesCount) {
            this.id = id;
            this.name = name;
            this.image = image;
            this.gamesCount = gamesCount;
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

        public int getGamesCount() {
            return gamesCount;
        }

        public void setGamesCount(int gamesCount) {
            this.gamesCount = gamesCount;
        }
    }

    public static class MostPlaces {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("image")
        private String image;
        @SerializedName("games_count")
        private int gamesCount;

        public MostPlaces(int id, String name, String image, int gamesCount) {
            this.id = id;
            this.name = name;
            this.image = image;
            this.gamesCount = gamesCount;
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

        public int getGamesCount() {
            return gamesCount;
        }

        public void setGamesCount(int gamesCount) {
            this.gamesCount = gamesCount;
        }
    }
}
