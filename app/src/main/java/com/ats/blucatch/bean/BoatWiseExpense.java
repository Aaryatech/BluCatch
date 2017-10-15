package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 28/9/17.
 */

public class BoatWiseExpense {

    private Integer trId;
    private long expId;
    private String expType;
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
    private Integer isAuthorised;
    private String autRemark;
    private String rejRemark;
    private long autDate;
    private long rejDate;
    private String remark;

    public BoatWiseExpense(Integer trId, long expId, String expType, String expName, long boatId, String boatName, long senderAccId, String senderAccName, long receiverAccId, String receiverAccName, double amount, double rate, Integer quantity, double total, double addedAmount, long transactionDate, Integer isAuthorised, String autRemark, String rejRemark, long autDate, long rejDate, String remark) {
        this.trId = trId;
        this.expId = expId;
        this.expType = expType;
        this.expName = expName;
        this.boatId = boatId;
        this.boatName = boatName;
        this.senderAccId = senderAccId;
        this.senderAccName = senderAccName;
        this.receiverAccId = receiverAccId;
        this.receiverAccName = receiverAccName;
        this.amount = amount;
        this.rate = rate;
        this.quantity = quantity;
        this.total = total;
        this.addedAmount = addedAmount;
        this.transactionDate = transactionDate;
        this.isAuthorised = isAuthorised;
        this.autRemark = autRemark;
        this.rejRemark = rejRemark;
        this.autDate = autDate;
        this.rejDate = rejDate;
        this.remark = remark;
    }

    public Integer getTrId() {
        return trId;
    }

    public void setTrId(Integer trId) {
        this.trId = trId;
    }

    public long getExpId() {
        return expId;
    }

    public void setExpId(long expId) {
        this.expId = expId;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
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

    public Integer getIsAuthorised() {
        return isAuthorised;
    }

    public void setIsAuthorised(Integer isAuthorised) {
        this.isAuthorised = isAuthorised;
    }

    public String getAutRemark() {
        return autRemark;
    }

    public void setAutRemark(String autRemark) {
        this.autRemark = autRemark;
    }

    public String getRejRemark() {
        return rejRemark;
    }

    public void setRejRemark(String rejRemark) {
        this.rejRemark = rejRemark;
    }

    public long getAutDate() {
        return autDate;
    }

    public void setAutDate(long autDate) {
        this.autDate = autDate;
    }

    public long getRejDate() {
        return rejDate;
    }

    public void setRejDate(long rejDate) {
        this.rejDate = rejDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public String toString() {
        return "BoatWiseExpense{" +
                "trId=" + trId +
                ", expId=" + expId +
                ", expType='" + expType + '\'' +
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
                ", isAuthorised=" + isAuthorised +
                ", autRemark='" + autRemark + '\'' +
                ", rejRemark='" + rejRemark + '\'' +
                ", autDate=" + autDate +
                ", rejDate=" + rejDate +
                ", remark='" + remark + '\'' +
                '}';
    }
}
