package com.helloWorldTech.funQuest.data.remote.api;

import com.google.gson.JsonElement;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;


public class ApiResponseAuth {
    public final Status status;

    @Nullable
    public final JsonElement data;

    @Nullable
    public final Throwable error;

    private ApiResponseAuth(Status status, @Nullable JsonElement data, @Nullable Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;
    }

    public static ApiResponseAuth loading() {
        return new ApiResponseAuth(Status.LOADING, null, null);
    }

    public static ApiResponseAuth success(@NonNull JsonElement data) {
        return new ApiResponseAuth(Status.SUCCESS, data, null);
    }

    public static ApiResponseAuth error(@NonNull Throwable error) {
        return new ApiResponseAuth(Status.ERROR, null, error);
    }

    public enum Status {
        LOADING,
        SUCCESS,
        ERROR
    }

}

