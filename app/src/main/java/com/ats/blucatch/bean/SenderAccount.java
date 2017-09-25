package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 21/9/17.
 */

public class SenderAccount {

    private long accId;
    private String accName;
    private String accType;
    private double accAmount;

    public SenderAccount(long accId, String accName, String accType, double accAmount) {
        this.accId = accId;
        this.accName = accName;
        this.accType = accType;
        this.accAmount = accAmount;
    }

    public long getAccId() {
        return accId;
    }

    public void setAccId(long accId) {
        this.accId = accId;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public double getAccAmount() {
        return accAmount;
    }

    public void setAccAmount(double accAmount) {
        this.accAmount = accAmount;
    }
}
