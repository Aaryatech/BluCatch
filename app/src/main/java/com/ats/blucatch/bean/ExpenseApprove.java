package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 20/9/17.
 */

public class ExpenseApprove {

    private int trId;
    private long expId;
    private String expName;
    private long boatId;
    private String boatName;
    private long senderAccId;
    private String senderAccName;
    private long receiverAccId;
    private String receiverAccName;
    private double amount;
    private double rate;
    private Integer quantity;
    private double total;
    private double addedAmount;
    private long transactionDate;

    public int getTrId() {
        return trId;
    }

    public void setTrId(int trId) {
        this.trId = trId;
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

    public long getBoatId() {
        return boatId;
    }

    public void setBoatId(long boatId) {
        this.boatId = boatId;
    }

    public String getBoatName() {
        return boatName;
    }

    public void setBoatName(String boatName) {
        this.boatName = boatName;
    }

    public long getSenderAccId() {
        return senderAccId;
    }

    public void setSenderAccId(long senderAccId) {
        this.senderAccId = senderAccId;
    }

    public String getSenderAccName() {
        return senderAccName;
    }

    public void setSenderAccName(String senderAccName) {
        this.senderAccName = senderAccName;
    }

    public long getReceiverAccId() {
        return receiverAccId;
    }

    public void setReceiverAccId(long receiverAccId) {
        this.receiverAccId = receiverAccId;
    }

    public String getReceiverAccName() {
        return receiverAccName;
    }

    public void setReceiverAccName(String receiverAccName) {
        this.receiverAccName = receiverAccName;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getAddedAmount() {
        return addedAmount;
    }

    public void setAddedAmount(double addedAmount) {
        this.addedAmount = addedAmount;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    @Override
    public String toString() {
        return "ExpenseApprove{" +
                "trId=" + trId +
                ", expId=" + expId +
                ", expName='" + expName + '\'' +
                ", boatId=" + boatId +
                ", boatName='" + boatName + '\'' +
                ", senderAccId=" + senderAccId +
                ", senderAccName='" + senderAccName + '\'' +
                ", receiverAccId=" + receiverAccId +
                ", receiverAccName='" + receiverAccName + '\'' +
                ", amount=" + amount +
                ", rate=" + rate +
                ", quantity=" + quantity +
                ", total=" + total +
                ", addedAmount=" + addedAmount +
                ", transactionDate=" + transactionDate +
                '}';
    }
}
