package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 25/9/17.
 */

public class FishCatchDisplayList {

    private Integer catchId;
    private long tripId;
    private Integer fishId;
    private String fishName;
    private double rate;
    private Integer quantity;
    private double total;
    private String weight;
    private Integer coId;
    private long enterDate;
    private long enterBy;

    public FishCatchDisplayList(Integer catchId, long tripId, Integer fishId, String fishName, double rate, Integer quantity, double total, String weight, Integer coId, long enterDate, long enterBy) {
        this.catchId = catchId;
        this.tripId = tripId;
        this.fishId = fishId;
        this.fishName = fishName;
        this.rate = rate;
        this.quantity = quantity;
        this.total = total;
        this.weight = weight;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public Integer getCatchId() {
        return catchId;
    }

    public void setCatchId(Integer catchId) {
        this.catchId = catchId;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public Integer getFishId() {
        return fishId;
    }

    public void setFishId(Integer fishId) {
        this.fishId = fishId;
    }

    public String getFishName() {
        return fishName;
    }

    public void setFishName(String fishName) {
        this.fishName = fishName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Integer getCoId() {
        return coId;
    }

    public void setCoId(Integer coId) {
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
        return "FishCatchDisplayList{" +
                "catchId=" + catchId +
                ", tripId=" + tripId +
                ", fishId=" + fishId +
                ", fishName='" + fishName + '\'' +
                ", rate=" + rate +
                ", quantity=" + quantity +
                ", total=" + total +
                ", weight='" + weight + '\'' +
                ", coId=" + coId +
                ", enterDate=" + enterDate +
                ", enterBy=" + enterBy +
                '}';
    }
}
