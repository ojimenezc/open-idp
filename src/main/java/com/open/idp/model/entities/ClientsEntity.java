package com.open.idp.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.Instant;

@Table(name = "customers", schema = "identity-provider")
@Entity
public class ClientsEntity {

    @Id
    @GeneratedValue
    @JsonProperty(value = "id")
    private int id;

    @JsonProperty(value = "name")
    @Column(name = "name")
    private String name;

    @JsonProperty(value = "email")
    @Column(name = "email")
    private String email;

    @JsonProperty(value = "username")
    @Column(name = "username")
    private String username;

    @JsonProperty(value = "password")
    @Column(name = "password")
    private String password;

    @JsonProperty(value = "biometric")
    @Column(name = "biometric")
    private Boolean biometric;

    @JsonProperty(value = "joiningDate")
    @Column(name = "joining_date")
    private Instant joiningDate;

    @JsonProperty(value = "lastUpdate")
    @Column(name = "last_update")
    private Instant lastUpdate;

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getBiometric() {
        return biometric;
    }

    public void setBiometric(Boolean biometric) {
        this.biometric = biometric;
    }

    public Instant getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(Instant joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Instant getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(Instant lastUpdate) {
        this.lastUpdate = lastUpdate;
    }
}
