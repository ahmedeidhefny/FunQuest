package com.helloWorldTech.funQuest.data.remote.modelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class HistoryApiResponse {

    @SerializedName("status")
    private Boolean status;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public HistoryApiResponse(Boolean status, int code, String message, Data data) {
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

        public Data(Games games) {
            this.games = games;
        }

        public Games getGames() {
            return games;
        }

        public void setGames(Games games) {
            this.games = games;
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
        @SerializedName("game_id")
        private int gameId;
        @SerializedName("game_name")
        private String gameName;
        @SerializedName("image")
        private String image;
        @SerializedName("date")
        private String date;
        @SerializedName("city_name")
        private String cityName;
        @SerializedName("place_name")
        private String placeName;
        @SerializedName("winner_team_name")
        private String winnerTeamName;

        public Items(int gameId, String gameName, String image, String date, String cityName, String placeName, String winnerTeamName) {
            this.gameId = gameId;
            this.gameName = gameName;
            this.image = image;
            this.date = date;
            this.cityName = cityName;
            this.placeName = placeName;
            this.winnerTeamName = winnerTeamName;
        }

        public int getGameId() {
            return gameId;
        }

        public void setGameId(int gameId) {
            this.gameId = gameId;
        }

        public String getGameName() {
            return gameName;
        }

        public void setGameName(String gameName) {
            this.gameName = gameName;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
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

        public String getWinnerTeamName() {
            return winnerTeamName;
        }

        public void setWinnerTeamName(String winnerTeamName) {
            this.winnerTeamName = winnerTeamName;
        }
    }
}
