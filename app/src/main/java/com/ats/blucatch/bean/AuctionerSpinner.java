package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 21/9/17.
 */

public class AuctionerSpinner {

    private long auctionerId;
    private String auctionerName;

    public long getAuctionerId() {
        return auctionerId;
    }

    public void setAuctionerId(long auctionerId) {
        this.auctionerId = auctionerId;
    }

    public String getAuctionerName() {
        return auctionerName;
    }

    public void setAuctionerName(String auctionerName) {
        this.auctionerName = auctionerName;
    }

    @Override
    public String toString() {
        return "AuctionerSpinner{" +
                "auctionerId=" + auctionerId +
                ", auctionerName='" + auctionerName + '\'' +
                '}';
    }
}
