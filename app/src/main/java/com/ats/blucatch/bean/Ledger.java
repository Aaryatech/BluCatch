package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 20/9/17.
 */

public class Ledger {

    private long accountId;
    private String accountName;
    private long expId;
    private String expName;
    private long transactionDate;
    private double amount;
    private String type;

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public long getExpId() {
        return expId;
    }

    public void setExpId(long expId) {
        this.expId = expId;
    }

    public String getExpName() {
        return expName;
    }

    public void setExpName(String expName) {
        this.expName = expName;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Ledger{" +
                "accountId=" + accountId +
                ", accountName='" + accountName + '\'' +
                ", expId=" + expId +
                ", expName='" + expName + '\'' +
                ", transactionDate=" + transactionDate +
                ", amount=" + amount +
                ", type='" + type + '\'' +
                '}';
    }
}
