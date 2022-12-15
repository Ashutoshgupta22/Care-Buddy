package com.aspark.carebuddy.retrofit;

import com.google.gson.annotations.SerializedName;

public class UserLoginResponse {

    @SerializedName("message")
    private String message;
    @SerializedName("status")
    private String status;

    public String getMessage() {
        return message;
    }

    public String getStatus() {
        return status;
    }
}
