package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 22/9/17.
 */

public class TripExpensesData {

    private Integer approveStatus;
    private long senderId;
    private long receiverId;
    private String expRemark;
    private long expDate;
    private long expTripId;
    private long expId;
    private String expName;
    private double expAmount;

    public TripExpensesData(Integer approveStatus, long senderId, long receiverId, String expRemark, long expDate, long expTripId, long expId, String expName, double expAmount) {
        this.approveStatus = approveStatus;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.expRemark = expRemark;
        this.expDate = expDate;
        this.expTripId = expTripId;
        this.expId = expId;
        this.expName = expName;
        this.expAmount = expAmount;
    }

    public Integer getApproveStatus() {
        return approveStatus;
    }

    public void setApproveStatus(Integer approveStatus) {
        this.approveStatus = approveStatus;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public String getExpRemark() {
        return expRemark;
    }

    public void setExpRemark(String expRemark) {
        this.expRemark = expRemark;
    }

    public long getExpDate() {
        return expDate;
    }

    public void setExpDate(long expDate) {
        this.expDate = expDate;
    }

    public long getExpTripId() {
        return expTripId;
    }

    public void setExpTripId(long expTripId) {
        this.expTripId = expTripId;
    }

    public long getExpId() {
        return expId;
    }

    public void setExpId(long expId) {
        this.expId = expId;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public double getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(double expAmount) {
        this.expAmount = expAmount;
    }

    @Override
    public String toString() {
        return "TripExpensesData{" +
                "approveStatus=" + approveStatus +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", expRemark='" + expRemark + '\'' +
                ", expDate=" + expDate +
                ", expTripId=" + expTripId +
                ", expId=" + expId +
                ", expName='" + expName + '\'' +
                ", expAmount=" + expAmount +
                '}';
    }
}
