package com.helloWorldTech.funQuest.data.remote.modelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GamesApiResponse {
    @SerializedName("status")
    private Boolean status;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public GamesApiResponse(Boolean status, int code, String message, Data data) {
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
        @SerializedName("games")
        private Games games;
        @SerializedName("place")
        private Place place;
        @SerializedName("city")
        private City city;

        public Data(Games games, Place place, City city) {
            this.games = games;
            this.place = place;
            this.city = city;
        }

        public Games getGames() {
            return games;
        }

        public void setGames(Games games) {
            this.games = games;
        }

        public Place getPlace() {
            return place;
        }

        public void setPlace(Place place) {
            this.place = place;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }
    }
    public static class Games {
        @SerializedName("current_page")
        private int currentPage;
        @SerializedName("from")
        private int from;
        @SerializedName("last_page")
        private int lastPage;
        @SerializedName("next_page_url")
        private String nextPageUrl;
        @SerializedName("path")
        private String path;
        @SerializedName("per_page")
        private int perPage;
        @SerializedName("prev_page_url")
        private String prevPageUrl;
        @SerializedName("to")
        private int to;
        @SerializedName("total")
        private int total;
        @SerializedName("items")
        private List<Items> items;

        public Games(int currentPage, int from, int lastPage, String nextPageUrl, String path, int perPage, String prevPageUrl, int to, int total, List<Items> items) {
            this.currentPage = currentPage;
            this.from = from;
            this.lastPage = lastPage;
            this.nextPageUrl = nextPageUrl;
            this.path = path;
            this.perPage = perPage;
            this.prevPageUrl = prevPageUrl;
            this.to = to;
            this.total = total;
            this.items = items;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLastPage() {
            return lastPage;
        }

        public void setLastPage(int lastPage) {
            this.lastPage = lastPage;
        }

        public String getNextPageUrl() {
            return nextPageUrl;
        }

        public void setNextPageUrl(String nextPageUrl) {
            this.nextPageUrl = nextPageUrl;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPerPage() {
            return perPage;
        }

        public void setPerPage(int perPage) {
            this.perPage = perPage;
        }

        public String getPrevPageUrl() {
            return prevPageUrl;
        }

        public void setPrevPageUrl(String prevPageUrl) {
            this.prevPageUrl = prevPageUrl;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<Items> getItems() {
            return items;
        }

        public void setItems(List<Items> items) {
            this.items = items;
        }
    }
    public static class Items {
        @SerializedName("id")
        private int id;
        @SerializedName("name")
        private String name;
        @SerializedName("start_date_time")
        private String startDateTime;
        @SerializedName("image")
        private String image;
        @SerializedName("teams_count")
        private int teamsCount;

        public Items(int id, String name, String startDateTime, String image, int teamsCount) {
            this.id = id;
            this.name = name;
            this.startDateTime = startDateTime;
            this.image = image;
            this.teamsCount = teamsCount;
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

        public String getStartDateTime() {
            return startDateTime;
        }

        public void setStartDateTime(String startDateTime) {
            this.startDateTime = startDateTime;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public int getTeamsCount() {
            return teamsCount;
        }

        public void setTeamsCount(int teamsCount) {
            this.teamsCount = teamsCount;
        }
    }

    public static class Place {
        @SerializedName("city_id")
        private int cityId;
        @SerializedName("name")
        private String name;

        public Place(int cityId, String name) {
            this.cityId = cityId;
            this.name = name;
        }

        public int getCityId() {
            return cityId;
        }

        public void setCityId(int cityId) {
            this.cityId = cityId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
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
}
