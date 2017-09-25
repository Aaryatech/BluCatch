package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 9/9/17.
 */

public class TripData {

    private List<Trip> trip;
    private ErrorMessage errorMessage;
    private List<TripDisplay> tripDisplay;

    public List<Trip> getTrip() {
        return trip;
    }

    public void setTrip(List<Trip> trip) {
        this.trip = trip;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<TripDisplay> getTripDisplay() {
        return tripDisplay;
    }

    public void setTripDisplay(List<TripDisplay> tripDisplay) {
        this.tripDisplay = tripDisplay;
    }

    @Override
    public String toString() {
        return "TripData{" +
                "trip=" + trip +
                ", errorMessage=" + errorMessage +
                ", tripDisplay=" + tripDisplay +
                '}';
    }
}
