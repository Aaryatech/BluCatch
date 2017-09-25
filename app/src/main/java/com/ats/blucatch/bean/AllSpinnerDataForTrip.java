package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 21/9/17.
 */

public class AllSpinnerDataForTrip {

    private List<TandelSpinner> tandelSpinner;
    private ErrorMessage errorMessage;
    private List<AuctionerSpinner> auctionerSpinner;

    public List<TandelSpinner> getTandelSpinner() {
        return tandelSpinner;
    }

    public void setTandelSpinner(List<TandelSpinner> tandelSpinner) {
        this.tandelSpinner = tandelSpinner;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<AuctionerSpinner> getAuctionerSpinner() {
        return auctionerSpinner;
    }

    public void setAuctionerSpinner(List<AuctionerSpinner> auctionerSpinner) {
        this.auctionerSpinner = auctionerSpinner;
    }

    @Override
    public String toString() {
        return "AllSpinnerDataForTrip{" +
                "tandelSpinner=" + tandelSpinner +
                ", errorMessage=" + errorMessage +
                ", auctionerSpinner=" + auctionerSpinner +
                '}';
    }
}
