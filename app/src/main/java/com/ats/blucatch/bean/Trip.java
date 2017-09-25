package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 9/9/17.
 */

public class Trip {

    private long tripId;
    private long boatId;
    private long tripStartDate;
    private long tripEndDate;
    private long tripTandelId;
    private long tripAuctionerId;
    private String tripStaff;
    private int tripSettled;
    private String tripStatus;
    private int tripDelete;
    private int coId;
    private long enterDate;
    private long enterBy;

    public Trip() {
    }

    public Trip(long tripId, long boatId, long tripStartDate, long tripEndDate, long tripTandelId, long tripAuctionerId, String tripStaff, int tripSettled, String tripStatus, int tripDelete, int coId, long enterDate, long enterBy) {
        this.tripId = tripId;
        this.boatId = boatId;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripTandelId = tripTandelId;
        this.tripAuctionerId = tripAuctionerId;
        this.tripStaff = tripStaff;
        this.tripSettled = tripSettled;
        this.tripStatus = tripStatus;
        this.tripDelete = tripDelete;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public Trip(long boatId, long tripStartDate, long tripEndDate, long tripTandelId, long tripAuctionerId, String tripStaff, int tripSettled, String tripStatus, int tripDelete, int coId, long enterDate, long enterBy) {
        this.boatId = boatId;
        this.tripStartDate = tripStartDate;
        this.tripEndDate = tripEndDate;
        this.tripTandelId = tripTandelId;
        this.tripAuctionerId = tripAuctionerId;
        this.tripStaff = tripStaff;
        this.tripSettled = tripSettled;
        this.tripStatus = tripStatus;
        this.tripDelete = tripDelete;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public long getBoatId() {
        return boatId;
    }

    public void setBoatId(long boatId) {
        this.boatId = boatId;
    }

    public long getTripStartDate() {
        return tripStartDate;
    }

    public void setTripStartDate(long tripStartDate) {
        this.tripStartDate = tripStartDate;
    }

    public long getTripEndDate() {
        return tripEndDate;
    }

    public void setTripEndDate(long tripEndDate) {
        this.tripEndDate = tripEndDate;
    }

    public long getTripTandelId() {
        return tripTandelId;
    }

    public void setTripTandelId(long tripTandelId) {
        this.tripTandelId = tripTandelId;
    }

    public long getTripAuctionerId() {
        return tripAuctionerId;
    }

    public void setTripAuctionerId(long tripAuctionerId) {
        this.tripAuctionerId = tripAuctionerId;
    }

    public String getTripStaff() {
        return tripStaff;
    }

    public void setTripStaff(String tripStaff) {
        this.tripStaff = tripStaff;
    }

    public int getTripSettled() {
        return tripSettled;
    }

    public void setTripSettled(int tripSettled) {
        this.tripSettled = tripSettled;
    }

    public String getTripStatus() {
        return tripStatus;
    }

    public void setTripStatus(String tripStatus) {
        this.tripStatus = tripStatus;
    }

    public int getTripDelete() {
        return tripDelete;
    }

    public void setTripDelete(int tripDelete) {
        this.tripDelete = tripDelete;
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
        return "Trip{" +
                "tripId=" + tripId +
                ", boatId=" + boatId +
                ", tripStartDate=" + tripStartDate +
                ", tripEndDate=" + tripEndDate +
                ", tripTandelId=" + tripTandelId +
                ", tripAuctionerId=" + tripAuctionerId +
                ", tripStaff=" + tripStaff +
                ", tripSettled=" + tripSettled +
                ", tripStatus='" + tripStatus + '\'' +
                ", tripDelete=" + tripDelete +
                ", coId=" + coId +
                ", enterDate=" + enterDate +
                ", enterBy=" + enterBy +
                '}';
    }
}

