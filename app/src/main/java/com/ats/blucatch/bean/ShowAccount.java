package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 19/9/17.
 */

public class ShowAccount {

    private Integer accId;
    private String accName;
    private String accType;
    private Integer accAmount;

    public ShowAccount(Integer accId, String accName, String accType, Integer accAmount) {
        this.accId = accId;
        this.accName = accName;
        this.accType = accType;
        this.accAmount = accAmount;
    }

    public Integer getAccId() {
        return accId;
    }

    public void setAccId(Integer accId) {
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

    public Integer getAccAmount() {
        return accAmount;
    }

    public void setAccAmount(Integer accAmount) {
        this.accAmount = accAmount;
    }

}
