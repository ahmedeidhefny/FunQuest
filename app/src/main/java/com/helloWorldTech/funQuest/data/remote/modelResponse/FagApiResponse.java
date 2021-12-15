package com.helloWorldTech.funQuest.data.remote.modelResponse;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;


public class FagApiResponse {

    @SerializedName("status")
    private Boolean status;
    @SerializedName("code")
    private int code;
    @SerializedName("message")
    private String message;
    @SerializedName("data")
    private Data data;

    public FagApiResponse(Boolean status, int code, String message, Data data) {
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
        @SerializedName("faq")
        private List<Faq> faq;

        public Data(List<Faq> faq) {
            this.faq = faq;
        }

        public List<Faq> getFaq() {
            return faq;
        }

        public void setFaq(List<Faq> faq) {
            this.faq = faq;
        }
    }
    public static class Faq implements Serializable {
        @SerializedName("id")
        private int id;
        @SerializedName("title")
        private String title;
        @SerializedName("content")
        private String content;
        @SerializedName("image")
        private String image;

        public Faq(int id, String title, String content, String image) {
            this.id = id;
            this.title = title;
            this.content = content;
            this.image = image;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }
    }
}
