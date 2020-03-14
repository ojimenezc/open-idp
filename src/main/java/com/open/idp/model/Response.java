package com.open.idp.model;

public class Response {
    public Response(String token) {
        this.access_token = token;
        this.token_type = "Bearer";
    }

    private String token_type;

    public String getToken_type() {
        return token_type;
    }

    public void setToken_type(String token_type) {
        this.token_type = token_type;
    }

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }

    private String access_token;

}
