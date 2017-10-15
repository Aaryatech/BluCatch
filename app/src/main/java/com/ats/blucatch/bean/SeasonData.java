package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 26/9/17.
 */

public class SeasonData {

    private List<Season> season;
    private ErrorMessage errorMessage;

    public List<Season> getSeason() {
        return season;
    }

    public void setSeason(List<Season> season) {
        this.season = season;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

}
