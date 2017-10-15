package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 25/9/17.
 */

public class FishSellList {

    private List<FishCatchDisplayList> fishCatchDisplayList;
    private long tripId;
    private double totalSell;
    private String auctionerName;
    private ErrorMessage errorMessage;

    public List<FishCatchDisplayList> getFishCatchDisplayList() {
        return fishCatchDisplayList;
    }

    public void setFishCatchDisplayList(List<FishCatchDisplayList> fishCatchDisplayList) {
        this.fishCatchDisplayList = fishCatchDisplayList;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public double getTotalSell() {
        return totalSell;
    }

    public void setTotalSell(double totalSell) {
        this.totalSell = totalSell;
    }

    public String getAuctionerName() {
        return auctionerName;
    }

    public void setAuctionerName(String auctionerName) {
        this.auctionerName = auctionerName;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "FishSellList{" +
                "fishCatchDisplayList=" + fishCatchDisplayList +
                ", tripId=" + tripId +
                ", totalSell=" + totalSell +
                ", auctionerName='" + auctionerName + '\'' +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
