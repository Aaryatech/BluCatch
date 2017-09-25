package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 19/9/17.
 */

public class Transaction {

    private int transactionId;
    private int seasonId;
    private long transactionDate;
    private long senderId;
    private long receiverId;
    private double amount;
    private String trType;
    private long tripId;
    private String expType;
    private double expRate;
    private int expQty;
    private double expTotal;
    private double expAddedAmt;
    private String expAddedAmtTitle;
    private String genRemark;
    private String genPhoto1;
    private String genPhoto2;
    private String genPhoto3;
    private int loginUserId;
    private long loginDate;
    private int isAuthorised;
    private String authRemark;
    private int authUserId;
    private long authDate;
    private String rejectRemark;
    private int rejectUserId;
    private long rejectDate;
    private long expId;
    private int coId;
    private long enterDate;
    private int enterBy;
    private int isSync;
    private long boatId;

    public Transaction() {
    }

    public Transaction(int seasonId, long transactionDate, long senderId, long receiverId, double amount, String trType, long tripId, String expType, double expRate, int expQty, double expTotal, double expAddedAmt, String expAddedAmtTitle, String genRemark, String genPhoto1, String genPhoto2, String genPhoto3, int loginUserId, long loginDate, int isAuthorised, String authRemark, int authUserId, long authDate, String rejectRemark, int rejectUserId, long rejectDate, long expId, int coId, long enterDate, int enterBy, int isSync, long boatId) {
        this.seasonId = seasonId;
        this.transactionDate = transactionDate;
        this.senderId = senderId;
        this.receiverId = receiverId;
        this.amount = amount;
        this.trType = trType;
        this.tripId = tripId;
        this.expType = expType;
        this.expRate = expRate;
        this.expQty = expQty;
        this.expTotal = expTotal;
        this.expAddedAmt = expAddedAmt;
        this.expAddedAmtTitle = expAddedAmtTitle;
        this.genRemark = genRemark;
        this.genPhoto1 = genPhoto1;
        this.genPhoto2 = genPhoto2;
        this.genPhoto3 = genPhoto3;
        this.loginUserId = loginUserId;
        this.loginDate = loginDate;
        this.isAuthorised = isAuthorised;
        this.authRemark = authRemark;
        this.authUserId = authUserId;
        this.authDate = authDate;
        this.rejectRemark = rejectRemark;
        this.rejectUserId = rejectUserId;
        this.rejectDate = rejectDate;
        this.expId = expId;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
        this.isSync = isSync;
        this.boatId = boatId;
    }

    public long getBoatId() {
        return boatId;
    }

    public void setBoatId(long boatId) {
        this.boatId = boatId;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public long getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(long transactionDate) {
        this.transactionDate = transactionDate;
    }

    public long getSenderId() {
        return senderId;
    }

    public void setSenderId(long senderId) {
        this.senderId = senderId;
    }

    public long getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(long receiverId) {
        this.receiverId = receiverId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getTrType() {
        return trType;
    }

    public void setTrType(String trType) {
        this.trType = trType;
    }

    public long getTripId() {
        return tripId;
    }

    public void setTripId(long tripId) {
        this.tripId = tripId;
    }

    public String getExpType() {
        return expType;
    }

    public void setExpType(String expType) {
        this.expType = expType;
    }

    public double getExpRate() {
        return expRate;
    }

    public void setExpRate(double expRate) {
        this.expRate = expRate;
    }

    public int getExpQty() {
        return expQty;
    }

    public void setExpQty(int expQty) {
        this.expQty = expQty;
    }

    public double getExpTotal() {
        return expTotal;
    }

    public void setExpTotal(double expTotal) {
        this.expTotal = expTotal;
    }

    public double getExpAddedAmt() {
        return expAddedAmt;
    }

    public void setExpAddedAmt(double expAddedAmt) {
        this.expAddedAmt = expAddedAmt;
    }

    public String getExpAddedAmtTitle() {
        return expAddedAmtTitle;
    }

    public void setExpAddedAmtTitle(String expAddedAmtTitle) {
        this.expAddedAmtTitle = expAddedAmtTitle;
    }

    public String getGenRemark() {
        return genRemark;
    }

    public void setGenRemark(String genRemark) {
        this.genRemark = genRemark;
    }

    public String getGenPhoto1() {
        return genPhoto1;
    }

    public void setGenPhoto1(String genPhoto1) {
        this.genPhoto1 = genPhoto1;
    }

    public String getGenPhoto2() {
        return genPhoto2;
    }

    public void setGenPhoto2(String genPhoto2) {
        this.genPhoto2 = genPhoto2;
    }

    public String getGenPhoto3() {
        return genPhoto3;
    }

    public void setGenPhoto3(String genPhoto3) {
        this.genPhoto3 = genPhoto3;
    }

    public int getLoginUserId() {
        return loginUserId;
    }

    public void setLoginUserId(int loginUserId) {
        this.loginUserId = loginUserId;
    }

    public long getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(long loginDate) {
        this.loginDate = loginDate;
    }

    public int getIsAuthorised() {
        return isAuthorised;
    }

    public void setIsAuthorised(int isAuthorised) {
        this.isAuthorised = isAuthorised;
    }

    public String getAuthRemark() {
        return authRemark;
    }

    public void setAuthRemark(String authRemark) {
        this.authRemark = authRemark;
    }

    public int getAuthUserId() {
        return authUserId;
    }

    public void setAuthUserId(int authUserId) {
        this.authUserId = authUserId;
    }

    public long getAuthDate() {
        return authDate;
    }

    public void setAuthDate(long authDate) {
        this.authDate = authDate;
    }

    public String getRejectRemark() {
        return rejectRemark;
    }

    public void setRejectRemark(String rejectRemark) {
        this.rejectRemark = rejectRemark;
    }

    public int getRejectUserId() {
        return rejectUserId;
    }

    public void setRejectUserId(int rejectUserId) {
        this.rejectUserId = rejectUserId;
    }

    public long getRejectDate() {
        return rejectDate;
    }

    public void setRejectDate(long rejectDate) {
        this.rejectDate = rejectDate;
    }

    public long getExpId() {
        return expId;
    }

    public void setExpId(long expId) {
        this.expId = expId;
    }

    public int getCoId() {
        return coId;
    }

    public void setCoId(int coId) {
        this.coId = coId;
    }

    public long getEnterDate() {
        return enterDate;
    }

    public void setEnterDate(long enterDate) {
        this.enterDate = enterDate;
    }

    public int getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(int enterBy) {
        this.enterBy = enterBy;
    }

    public int getIsSync() {
        return isSync;
    }

    public void setIsSync(int isSync) {
        this.isSync = isSync;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "transactionId=" + transactionId +
                ", seasonId=" + seasonId +
                ", transactionDate=" + transactionDate +
                ", senderId=" + senderId +
                ", receiverId=" + receiverId +
                ", amount=" + amount +
                ", trType='" + trType + '\'' +
                ", tripId=" + tripId +
                ", expType='" + expType + '\'' +
                ", expRate=" + expRate +
                ", expQty=" + expQty +
                ", expTotal=" + expTotal +
                ", expAddedAmt=" + expAddedAmt +
                ", expAddedAmtTitle='" + expAddedAmtTitle + '\'' +
                ", genRemark='" + genRemark + '\'' +
                ", genPhoto1='" + genPhoto1 + '\'' +
                ", genPhoto2='" + genPhoto2 + '\'' +
                ", genPhoto3='" + genPhoto3 + '\'' +
                ", loginUserId=" + loginUserId +
                ", loginDate=" + loginDate +
                ", isAuthorised=" + isAuthorised +
                ", authRemark='" + authRemark + '\'' +
                ", authUserId=" + authUserId +
                ", authDate=" + authDate +
                ", rejectRemark='" + rejectRemark + '\'' +
                ", rejectUserId=" + rejectUserId +
                ", rejectDate=" + rejectDate +
                ", expId=" + expId +
                ", coId=" + coId +
                ", enterDate=" + enterDate +
                ", enterBy=" + enterBy +
                ", isSync=" + isSync +
                ", boatId=" + boatId +
                '}';
    }
}
