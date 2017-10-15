package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 10/10/17.
 */

public class TripSettleTransactions {


    private long toAccId;
    private double amount;
    private long tripId;
    private int coId;
    private int userId;
    private int seasonId;

    public TripSettleTransactions(long toAccId, double amount, long tripId, int coId, int userId, int seasonId) {
        this.toAccId = toAccId;
        this.amount = amount;
        this.tripId = tripId;
        this.coId = coId;
        this.userId = userId;
        this.seasonId = seasonId;
    }

    public long getToAccId() {
        return toAccId;
    }

    public void setToAccId(long toAccId) {
        this.toAccId = toAccId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public int getCoId() {
        return coId;
    }

    public void setCoId(int coId) {
        this.coId = coId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    @Override
    public String toString() {
        return "TripSettleTransactions{" +
                "toAccId=" + toAccId +
                ", amount=" + amount +
                ", tripId=" + tripId +
                ", coId=" + coId +
                ", userId=" + userId +
                ", seasonId=" + seasonId +
                '}';
    }
}
