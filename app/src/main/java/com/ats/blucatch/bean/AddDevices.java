package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 11/10/17.
 */

public class AddDevices {

    private int deviceId;
    private int userId;
    private long accId;
    private String token;
    private int coId;

    public AddDevices(int userId, long accId, String token,int coId) {
        this.userId = userId;
        this.accId = accId;
        this.token = token;
        this.coId=coId;
    }

    public int getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(int deviceId) {
        this.deviceId = deviceId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getAccId() {
        return accId;
    }

    public void setAccId(long accId) {
        this.accId = accId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getCoId() {
        return coId;
    }

    public void setCoId(int coId) {
        this.coId = coId;
    }

    @Override
    public String toString() {
        return "AddDevices{" +
                "deviceId=" + deviceId +
                ", userId=" + userId +
                ", accId=" + accId +
                ", token='" + token + '\'' +
                ", coId=" + coId +
                '}';
    }
}
