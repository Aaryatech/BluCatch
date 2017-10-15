package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 11/10/17.
 */

public class Notification {

    private Integer notificationId;
    private Integer userId;
    private String title;
    private String description;
    private Integer coId;
    private long onDate;

    public Integer getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(Integer notificationId) {
        this.notificationId = notificationId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getCoId() {
        return coId;
    }

    public void setCoId(Integer coId) {
        this.coId = coId;
    }

    public long getOnDate() {
        return onDate;
    }

    public void setOnDate(long onDate) {
        this.onDate = onDate;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", coId=" + coId +
                ", onDate=" + onDate +
                '}';
    }
}
