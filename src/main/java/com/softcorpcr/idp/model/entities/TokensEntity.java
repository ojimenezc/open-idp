package com.softcorpcr.idp.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "tokens", schema = "identity-provider")
@Entity
public class TokensEntity {

    @Id
    @GeneratedValue
    @JsonProperty(value = "id")
    private int id;

    @JsonProperty(value = "token")
    @Column(name = "token")
    private String token;
    @JsonProperty(value = "creationDate")
    @Column(name = "creation_date")
    private Instant creationDate;

    @JsonProperty(value = "expiryDate")
    @Column(name = "expiry_date")
    private Instant expiryDate;

    @JsonProperty(value = "clientCredentials")
    @Column(name = "client_credentials")
    private String clientCredentials;

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    @JsonProperty(value = "grantType")
    @Column(name = "grant_type")
    private String grantType;

    public String getClientCredentials() {
        return clientCredentials;
    }

    public void setClientCredentials(String clientCredentials) {
        this.clientCredentials = clientCredentials;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Instant creationDate) {
        this.creationDate = creationDate;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }
}
