package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 21/9/17.
 */

public class ReceiverAccount {

    private long expId;
    private String expName;
    private String expType;
    private String expEntry;
    private String expCombo;
    private Integer expPhoto;
    private double expAmount;
    private String expAccess;

    public ReceiverAccount(long expId, String expName, String expType, String expEntry, String expCombo, Integer expPhoto, double expAmount, String expAccess) {
        this.expId = expId;
        this.expName = expName;
        this.expType = expType;
        this.expEntry = expEntry;
        this.expCombo = expCombo;
        this.expPhoto = expPhoto;
        this.expAmount = expAmount;
        this.expAccess = expAccess;
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

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public String getExpEntry() {
        return expEntry;
    }

    public void setExpEntry(String expEntry) {
        this.expEntry = expEntry;
    }

    public String getExpCombo() {
        return expCombo;
    }

    public void setExpCombo(String expCombo) {
        this.expCombo = expCombo;
    }

    public Integer getExpPhoto() {
        return expPhoto;
    }

    public void setExpPhoto(Integer expPhoto) {
        this.expPhoto = expPhoto;
    }

    public double getExpAmount() {
        return expAmount;
    }

    public void setExpAmount(double expAmount) {
        this.expAmount = expAmount;
    }

    public String getExpAccess() {
        return expAccess;
    }

    public void setExpAccess(String expAccess) {
        this.expAccess = expAccess;
    }

    @Override
    public String toString() {
        return "ReceiverAccount{" +
                "expId=" + expId +
                ", expName='" + expName + '\'' +
                ", expType='" + expType + '\'' +
                ", expEntry='" + expEntry + '\'' +
                ", expCombo='" + expCombo + '\'' +
                ", expPhoto=" + expPhoto +
                ", expAmount=" + expAmount +
                ", expAccess='" + expAccess + '\'' +
                '}';
    }
}
