package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 9/9/17.
 */

public class TripDisplay {

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
    private String boatName;
    private String tandelName;
    private String auctionerName;
    private int coId;
    private long enterDate;
    private long enterBy;
    private int seasonId;
    private double expenseCount;
    private double fishSellCount;

    public TripDisplay() {
    }

    public TripDisplay(long tripId, long boatId, long tripStartDate, long tripEndDate, long tripTandelId, long tripAuctionerId, String tripStaff, int tripSettled, String tripStatus, int tripDelete, String boatName, String tandelName, String auctionerName, int coId, long enterDate, long enterBy, int seasonId, double expenseCount, double fishSellCount) {
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
        this.boatName = boatName;
        this.tandelName = tandelName;
        this.auctionerName = auctionerName;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
        this.seasonId = seasonId;
        this.expenseCount = expenseCount;
        this.fishSellCount = fishSellCount;
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

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public String getTandelName() {
        return tandelName;
    }

    public void setTandelName(String tandelName) {
        this.tandelName = tandelName;
    }

    public String getAuctionerName() {
        return auctionerName;
    }

    public void setAuctionerName(String auctionerName) {
        this.auctionerName = auctionerName;
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

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public double getExpenseCount() {
        return expenseCount;
    }

    public void setExpenseCount(double expenseCount) {
        this.expenseCount = expenseCount;
    }

    public double getFishSellCount() {
        return fishSellCount;
    }

    public void setFishSellCount(double fishSellCount) {
        this.fishSellCount = fishSellCount;
    }

    @Override
    public String toString() {
        return "TripDisplay{" +
                "tripId=" + tripId +
                ", boatId=" + boatId +
                ", tripStartDate=" + tripStartDate +
                ", tripEndDate=" + tripEndDate +
                ", tripTandelId=" + tripTandelId +
                ", tripAuctionerId=" + tripAuctionerId +
                ", tripStaff='" + tripStaff + '\'' +
                ", tripSettled=" + tripSettled +
                ", tripStatus='" + tripStatus + '\'' +
                ", tripDelete=" + tripDelete +
                ", boatName='" + boatName + '\'' +
                ", tandelName='" + tandelName + '\'' +
                ", auctionerName='" + auctionerName + '\'' +
                ", coId=" + coId +
                ", enterDate=" + enterDate +
                ", enterBy=" + enterBy +
                ", seasonId=" + seasonId +
                ", expenseCount=" + expenseCount +
                ", fishSellCount=" + fishSellCount +
                '}';
    }
}
