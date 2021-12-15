package com.helloWorldTech.funQuest.data.remote.modelResponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AwardsApiResponse {


    @SerializedName("status")
    private Boolean status;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public AwardsApiResponse(Boolean status, int code, String message, Data data) {
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
        @SerializedName("current_page")
        private int currentPage;
        @SerializedName("data")
        private List<Award> awards;
        @SerializedName("first_page_url")
        private String firstPageUrl;
        @SerializedName("from")
        private int from;
        @SerializedName("last_page")
        private int lastPage;
        @SerializedName("last_page_url")
        private String lastPageUrl;
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

        public Data(int currentPage, List<Award> awards, String firstPageUrl, int from, int lastPage, String lastPageUrl, String nextPageUrl, String path, int perPage, String prevPageUrl, int to, int total) {
            this.currentPage = currentPage;
            this.awards = awards;
            this.firstPageUrl = firstPageUrl;
            this.from = from;
            this.lastPage = lastPage;
            this.lastPageUrl = lastPageUrl;
            this.nextPageUrl = nextPageUrl;
            this.path = path;
            this.perPage = perPage;
            this.prevPageUrl = prevPageUrl;
            this.to = to;
            this.total = total;
        }

        public int getCurrentPage() {
            return currentPage;
        }

        public void setCurrentPage(int currentPage) {
            this.currentPage = currentPage;
        }

        public List<Award> getAwards() {
            return awards;
        }

        public void setAwards(List<Award> awards) {
            this.awards = awards;
        }

        public String getFirstPageUrl() {
            return firstPageUrl;
        }

        public void setFirstPageUrl(String firstPageUrl) {
            this.firstPageUrl = firstPageUrl;
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

        public String getLastPageUrl() {
            return lastPageUrl;
        }

        public void setLastPageUrl(String lastPageUrl) {
            this.lastPageUrl = lastPageUrl;
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
    }
    public static class Award {
        @SerializedName("title")
        private String title;
        @SerializedName("readed_at")
        private String readedAt;
        @SerializedName("created_at")
        private String createdAt;

        public Award(String title, String readedAt, String createdAt) {
            this.title = title;
            this.readedAt = readedAt;
            this.createdAt = createdAt;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getReadedAt() {
            return readedAt;
        }

        public void setReadedAt(String readedAt) {
            this.readedAt = readedAt;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}
