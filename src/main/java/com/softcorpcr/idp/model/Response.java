package com.softcorpcr.idp.model;

public class Response {
    public Response(String token) {
        this.token = token;
    }

    public String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
