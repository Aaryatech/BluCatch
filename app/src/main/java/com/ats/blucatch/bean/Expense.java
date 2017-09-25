package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 10/9/17.
 */

public class Expense {

    private long expId;
    private String expName;
    private String expType;
    private String expAccType;
    private String expEntryType;
    private int expIsPhotoReq;
    private String expAccessTo;
    private int expIsUsed;
    private int coId;
    private long enterDate;
    private long enterBy;
    private String comboValues;

    public Expense() {
    }

    public Expense(long expId, String expName, String expType, String expAccType, String expEntryType, int expIsPhotoReq, String expAccessTo, int expIsUsed, int coId, long enterDate, long enterBy, String comboValues) {
        this.expId = expId;
        this.expName = expName;
        this.expType = expType;
        this.expAccType = expAccType;
        this.expEntryType = expEntryType;
        this.expIsPhotoReq = expIsPhotoReq;
        this.expAccessTo = expAccessTo;
        this.expIsUsed = expIsUsed;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
        this.comboValues = comboValues;
    }

    public Expense(String expName, String expType, String expAccType, String expEntryType, int expIsPhotoReq, String expAccessTo, int expIsUsed, int coId, long enterDate, long enterBy, String comboValues) {
        this.expName = expName;
        this.expType = expType;
        this.expAccType = expAccType;
        this.expEntryType = expEntryType;
        this.expIsPhotoReq = expIsPhotoReq;
        this.expAccessTo = expAccessTo;
        this.expIsUsed = expIsUsed;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
        this.comboValues = comboValues;
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

    public String getExpAccType() {
        return expAccType;
    }

    public void setExpAccType(String expAccType) {
        this.expAccType = expAccType;
    }

    public String getExpEntryType() {
        return expEntryType;
    }

    public void setExpEntryType(String expEntryType) {
        this.expEntryType = expEntryType;
    }

    public int getExpIsPhotoReq() {
        return expIsPhotoReq;
    }

    public void setExpIsPhotoReq(int expIsPhotoReq) {
        this.expIsPhotoReq = expIsPhotoReq;
    }

    public String getExpAccessTo() {
        return expAccessTo;
    }

    public void setExpAccessTo(String expAccessTo) {
        this.expAccessTo = expAccessTo;
    }

    public int getExpIsUsed() {
        return expIsUsed;
    }

    public void setExpIsUsed(int expIsUsed) {
        this.expIsUsed = expIsUsed;
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

    public String getComboValues() {
        return comboValues;
    }

    public void setComboValues(String comboValues) {
        this.comboValues = comboValues;
    }

    @Override
    public String toString() {
        return "Expense{" +
                "expId=" + expId +
                ", expName='" + expName + '\'' +
                ", expType='" + expType + '\'' +
                ", expAccType='" + expAccType + '\'' +
                ", expEntryType='" + expEntryType + '\'' +
                ", expIsPhotoReq=" + expIsPhotoReq +
                ", expAccessTo='" + expAccessTo + '\'' +
                ", expIsUsed=" + expIsUsed +
                ", coId=" + coId +
                ", enterDate=" + enterDate +
                ", enterBy=" + enterBy +
                ", comboValues='" + comboValues + '\'' +
                '}';
    }
}
