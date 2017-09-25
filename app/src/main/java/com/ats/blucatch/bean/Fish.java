package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 7/9/17.
 */

public class Fish {

    private int fishId;
    private String fishName;
    private String fishType;
    private int fishIsUsed;
    private String fishSize;
    private double fishMinRate;
    private double fishMaxRate;
    private int coId;
    private long enterDate;
    private long enterBy;

    public Fish() {
    }

    public Fish(int fishId, String fishName, String fishType, int fishIsUsed, String fishSize, double fishMinRate, double fishMaxRate, int coId, long enterDate, long enterBy) {
        this.fishId = fishId;
        this.fishName = fishName;
        this.fishType = fishType;
        this.fishIsUsed = fishIsUsed;
        this.fishSize = fishSize;
        this.fishMinRate = fishMinRate;
        this.fishMaxRate = fishMaxRate;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public Fish(String fishName, String fishType, int fishIsUsed, String fishSize, double fishMinRate, double fishMaxRate, int coId, long enterDate, long enterBy) {
        this.fishName = fishName;
        this.fishType = fishType;
        this.fishIsUsed = fishIsUsed;
        this.fishSize = fishSize;
        this.fishMinRate = fishMinRate;
        this.fishMaxRate = fishMaxRate;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public int getFishId() {
        return fishId;
    }

    public void setFishId(int fishId) {
        this.fishId = fishId;
    }

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public String getFishType() {
        return fishType;
    }

    public void setFishType(String fishType) {
        this.fishType = fishType;
    }

    public int getFishIsUsed() {
        return fishIsUsed;
    }

    public void setFishIsUsed(int fishIsUsed) {
        this.fishIsUsed = fishIsUsed;
    }

    public String getFishSize() {
        return fishSize;
    }

    public void setFishSize(String fishSize) {
        this.fishSize = fishSize;
    }

    public double getFishMinRate() {
        return fishMinRate;
    }

    public void setFishMinRate(double fishMinRate) {
        this.fishMinRate = fishMinRate;
    }

    public double getFishMaxRate() {
        return fishMaxRate;
    }

    public void setFishMaxRate(double fishMaxRate) {
        this.fishMaxRate = fishMaxRate;
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
        return "Fish{" +
                "fishId=" + fishId +
                ", fishName='" + fishName + '\'' +
                ", fishType='" + fishType + '\'' +
                ", fishIsUsed=" + fishIsUsed +
                ", fishSize='" + fishSize + '\'' +
                ", fishMinRate=" + fishMinRate +
                ", fishMaxRate=" + fishMaxRate +
                ", coId=" + coId +
                ", enterDate=" + enterDate +
                ", enterBy=" + enterBy +
                '}';
    }
}
