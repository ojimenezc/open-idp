package com.softcorpcr.idp.model.entities;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Table(name = "applications", schema = "identity-provider")
@Entity
public class ApplicationEntity {
    @Id
    @GeneratedValue
    @JsonProperty(value = "id")
    private int id;

    @JsonProperty(value = "clientId")
    @Column(name = "client_id")
    private String clientId;

    @JsonProperty(value = "clientSecret")
    @Column(name = "client_secret")
    private String clientSecret;

    @JsonProperty(value = "enabled")
    @Column(name = "enabled")
    private Boolean enabled;

    @JsonProperty(value = "customerId")
    @Column(name = "customer_id")
    private int customerId;

    @JsonProperty(value = "creationDate")
    @Column(name = "creation_date")
    private Date creationDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
