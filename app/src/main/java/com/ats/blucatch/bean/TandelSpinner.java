package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 21/9/17.
 */

public class TandelSpinner {

    private long tandelId;
    private String tandelName;

    public long getTandelId() {
        return tandelId;
    }

    public void setTandelId(long tandelId) {
        this.tandelId = tandelId;
    }

    public String getTandelName() {
        return tandelName;
    }

    public void setTandelName(String tandelName) {
        this.tandelName = tandelName;
    }

    @Override
    public String toString() {
        return "TandelSpinner{" +
                "tandelId=" + tandelId +
                ", tandelName='" + tandelName + '\'' +
                '}';
    }
}
