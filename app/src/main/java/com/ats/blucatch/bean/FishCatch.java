package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 25/9/17.
 */

public class FishCatch {

    private int catchId;
    private long tripId;
    private int fishId;
    private double rate;
    private int quantity;
    private double total;
    private String weight;
    private int coId;
    private long enterDate;
    private long enterBy;

    public FishCatch(long tripId, int fishId, double rate, int quantity, double total, String weight, int coId, long enterDate, long enterBy) {
        this.tripId = tripId;
        this.fishId = fishId;
        this.rate = rate;
        this.quantity = quantity;
        this.total = total;
        this.weight = weight;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public int getCatchId() {
        return catchId;
    }

    public void setCatchId(int catchId) {
        this.catchId = catchId;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public int getFishId() {
        return fishId;
    }

    public void setFishId(int fishId) {
        this.fishId = fishId;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
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
        return "FishCatch{" +
                "catchId=" + catchId +
                ", tripId=" + tripId +
                ", fishId=" + fishId +
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
