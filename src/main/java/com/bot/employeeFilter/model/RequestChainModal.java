package com.bot.employeeFilter.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public class RequestChainModal {
    @JsonProperty("ExecuterId")
    Long executerId;
    @JsonProperty("IsActive")
    boolean isActive;
    @JsonProperty("ExecuterEmail")
    String executerEmail;
    @JsonProperty("Status")
    int status;
    @JsonProperty("ReactedOn")
    @JsonFormat(pattern="yyyy-MM-dd'T'HH:mm:ss")
    Date reactedOn;
    @JsonProperty("Level")
    int level;
    @JsonProperty("FeedBack")
    String feedBack;
    @JsonProperty("ForwardAfterDays")
    int forwardAfterDays;
    @JsonProperty("ForwardWhenStatus")
    int forwardWhenStatus;
    @JsonProperty("IsRequired")
    boolean isRequired;

    public Long getExecuterId() {
        return executerId;
    }

    public void setExecuterId(Long executerId) {
        this.executerId = executerId;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getExecuterEmail() {
        return executerEmail;
    }

    public void setExecuterEmail(String executerEmail) {
        this.executerEmail = executerEmail;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getReactedOn() {
        return reactedOn;
    }

    public void setReactedOn(Date reactedOn) {
        this.reactedOn = reactedOn;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getFeedBack() {
        return feedBack;
    }

    public void setFeedBack(String feedBack) {
        this.feedBack = feedBack;
    }

    public int getForwardAfterDays() {
        return forwardAfterDays;
    }

    public void setForwardAfterDays(int forwardAfterDays) {
        this.forwardAfterDays = forwardAfterDays;
    }

    public int getForwardWhenStatus() {
        return forwardWhenStatus;
    }

    public void setForwardWhenStatus(int forwardWhenStatus) {
        this.forwardWhenStatus = forwardWhenStatus;
    }

    public boolean isRequired() {
        return isRequired;
    }

    public void setRequired(boolean required) {
        isRequired = required;
    }
}
