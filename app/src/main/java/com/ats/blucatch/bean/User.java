package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 7/9/17.
 */

public class User {

    private int userId;
    private long accId;
    private String userName;
    private String userPassword;
    private String userAccessType;
    private int isUsed;
    private int blockStatus;
    private int coId;
    private long enterDate;
    private long enterBy;

    public User() {
    }

    public User(int userId, long accId, String userName, String userPassword, String userAccessType, int isUsed, int blockStatus, int coId, long enterDate, long enterBy) {
        this.userId = userId;
        this.accId = accId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userAccessType = userAccessType;
        this.isUsed = isUsed;
        this.blockStatus = blockStatus;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public User(long accId, String userName, String userPassword, String userAccessType, int isUsed, int blockStatus, int coId, long enterDate, long enterBy) {
        this.accId = accId;
        this.userName = userName;
        this.userPassword = userPassword;
        this.userAccessType = userAccessType;
        this.isUsed = isUsed;
        this.blockStatus = blockStatus;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getUserAccessType() {
        return userAccessType;
    }

    public void setUserAccessType(String userAccessType) {
        this.userAccessType = userAccessType;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public int getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(int blockStatus) {
        this.blockStatus = blockStatus;
    }

    public int getCoId() {
        return coId;
    }

    public void setCoId(int coId) {
        this.coId = coId;
    }

    public long getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(long enterDate) {
        this.enterDate = enterDate;
    }

    public long getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(long enterBy) {
        this.enterBy = enterBy;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", accId=" + accId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userAccessType='" + userAccessType + '\'' +
                ", isUsed=" + isUsed +
                ", blockStatus=" + blockStatus +
                ", coId=" + coId +
                ", enterDate=" + enterDate +
                ", enterBy=" + enterBy +
                '}';
    }
}
