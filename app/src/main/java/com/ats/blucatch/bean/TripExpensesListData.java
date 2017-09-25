package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 22/9/17.
 */

public class TripExpensesListData {

    private List<TripExpensesData> tripExpensesData;
    private Integer totalAmount;
    private Integer tripId;
    private ErrorMessage errorMessage;

    public List<TripExpensesData> getTripExpensesData() {
        return tripExpensesData;
    }

    public void setTripExpensesData(List<TripExpensesData> tripExpensesData) {
        this.tripExpensesData = tripExpensesData;
    }

    public Integer getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(Integer totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getTripId() {
        return tripId;
    }

    public void setTripId(Integer tripId) {
        this.tripId = tripId;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
