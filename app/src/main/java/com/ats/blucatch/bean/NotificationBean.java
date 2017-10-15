package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 11/10/17.
 */

public class NotificationBean {

    private int notificationId;
    private int userId;
    private String title;
    private String description;
    private int coId;

    public NotificationBean(int userId, String title, String description, int coId) {
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.coId = coId;
    }

    public int getNotificationId() {
        return notificationId;
    }

    public void setNotificationId(int notificationId) {
        this.notificationId = notificationId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
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

    public int getCoId() {
        return coId;
    }

    public void setCoId(int coId) {
        this.coId = coId;
    }

    @Override
    public String toString() {
        return "NotificationBean{" +
                "notificationId=" + notificationId +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", coId=" + coId +
                '}';
    }

}
