package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 9/9/17.
 */

public class BoatData {

    private List<Boat> boat;
    private ErrorMessage errorMessage;
    private List<BoatDisp> boatDisp;

    public List<Boat> getBoat() {
        return boat;
    }

    public void setBoat(List<Boat> boat) {
        this.boat = boat;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    public List<BoatDisp> getBoatDisp() {
        return boatDisp;
    }

    public void setBoatDisp(List<BoatDisp> boatDisp) {
        this.boatDisp = boatDisp;
    }

}
