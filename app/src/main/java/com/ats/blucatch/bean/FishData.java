package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 7/9/17.
 */

public class FishData {

    private List<Fish> fish;
    private ErrorMessage errorMessage;

    public List<Fish> getFish() {
        return fish;
    }

    public void setFish(List<Fish> fish) {
        this.fish = fish;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "FishData{" +
                "fish=" + fish +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
