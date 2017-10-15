package com.ats.blucatch.bean;

import java.util.List;


public class NotificationData {

    private List<Notification> notification;
    private ErrorMessage errorMessage;

    public List<Notification> getNotification() {
        return notification;
    }

    public void setNotification(List<Notification> notification) {
        this.notification = notification;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "NotificationData{" +
                "notification=" + notification +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
