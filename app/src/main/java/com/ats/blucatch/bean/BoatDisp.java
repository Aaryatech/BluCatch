package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 9/9/17.
 */

public class BoatDisp {

    private long boatId;
    private String boatName;
    private long boatOwner;
    private String regNo;
    private long boatPurchaseDate;
    private int boatLenght;
    private int boatBreadth;
    private int boatHeight;
    private String fishingType;
    private String boatLicNo;
    private long boatLicFromDt;
    private long boatLicToDt;
    private long boatInsuranceFromDt;
    private long boatInsuranceToDt;
    private String boatEngineType;
    private int boatHPRating;
    private int boatIsUsed;
    private long boatTandel;
    private long boatAuctioner;
    private String boatStatus;
    private int blockStatus;
    private String tandelName;
    private String auctionerName;
    private String ownerName;
    private int coId;
    private long enterDate;
    private long enterBy;

    public BoatDisp() {
    }

    public BoatDisp(long boatId, String boatName, long boatOwner, String regNo, long boatPurchaseDate, int boatLenght, int boatBreadth, int boatHeight, String fishingType, String boatLicNo, long boatLicFromDt, long boatLicToDt, long boatInsuranceFromDt, long boatInsuranceToDt, String boatEngineType, int boatHPRating, int boatIsUsed, long boatTandel, long boatAuctioner, String boatStatus, int blockStatus, String tandelName, String auctionerName, String ownerName, int coId, long enterDate, long enterBy) {
        this.boatId = boatId;
        this.boatName = boatName;
        this.boatOwner = boatOwner;
        this.regNo = regNo;
        this.boatPurchaseDate = boatPurchaseDate;
        this.boatLenght = boatLenght;
        this.boatBreadth = boatBreadth;
        this.boatHeight = boatHeight;
        this.fishingType = fishingType;
        this.boatLicNo = boatLicNo;
        this.boatLicFromDt = boatLicFromDt;
        this.boatLicToDt = boatLicToDt;
        this.boatInsuranceFromDt = boatInsuranceFromDt;
        this.boatInsuranceToDt = boatInsuranceToDt;
        this.boatEngineType = boatEngineType;
        this.boatHPRating = boatHPRating;
        this.boatIsUsed = boatIsUsed;
        this.boatTandel = boatTandel;
        this.boatAuctioner = boatAuctioner;
        this.boatStatus = boatStatus;
        this.blockStatus = blockStatus;
        this.tandelName = tandelName;
        this.auctionerName = auctionerName;
        this.ownerName = ownerName;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
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

    public long getBoatOwner() {
        return boatOwner;
    }

    public void setBoatOwner(long boatOwner) {
        this.boatOwner = boatOwner;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public long getBoatPurchaseDate() {
        return boatPurchaseDate;
    }

    public void setBoatPurchaseDate(long boatPurchaseDate) {
        this.boatPurchaseDate = boatPurchaseDate;
    }

    public int getBoatLenght() {
        return boatLenght;
    }

    public void setBoatLenght(int boatLenght) {
        this.boatLenght = boatLenght;
    }

    public int getBoatBreadth() {
        return boatBreadth;
    }

    public void setBoatBreadth(int boatBreadth) {
        this.boatBreadth = boatBreadth;
    }

    public int getBoatHeight() {
        return boatHeight;
    }

    public void setBoatHeight(int boatHeight) {
        this.boatHeight = boatHeight;
    }

    public String getFishingType() {
        return fishingType;
    }

    public void setFishingType(String fishingType) {
        this.fishingType = fishingType;
    }

    public String getBoatLicNo() {
        return boatLicNo;
    }

    public void setBoatLicNo(String boatLicNo) {
        this.boatLicNo = boatLicNo;
    }

    public long getBoatLicFromDt() {
        return boatLicFromDt;
    }

    public void setBoatLicFromDt(long boatLicFromDt) {
        this.boatLicFromDt = boatLicFromDt;
    }

    public long getBoatLicToDt() {
        return boatLicToDt;
    }

    public void setBoatLicToDt(long boatLicToDt) {
        this.boatLicToDt = boatLicToDt;
    }

    public long getBoatInsuranceFromDt() {
        return boatInsuranceFromDt;
    }

    public void setBoatInsuranceFromDt(long boatInsuranceFromDt) {
        this.boatInsuranceFromDt = boatInsuranceFromDt;
    }

    public long getBoatInsuranceToDt() {
        return boatInsuranceToDt;
    }

    public void setBoatInsuranceToDt(long boatInsuranceToDt) {
        this.boatInsuranceToDt = boatInsuranceToDt;
    }

    public String getBoatEngineType() {
        return boatEngineType;
    }

    public void setBoatEngineType(String boatEngineType) {
        this.boatEngineType = boatEngineType;
    }

    public int getBoatHPRating() {
        return boatHPRating;
    }

    public void setBoatHPRating(int boatHPRating) {
        this.boatHPRating = boatHPRating;
    }

    public int getBoatIsUsed() {
        return boatIsUsed;
    }

    public void setBoatIsUsed(int boatIsUsed) {
        this.boatIsUsed = boatIsUsed;
    }

    public long getBoatTandel() {
        return boatTandel;
    }

    public void setBoatTandel(long boatTandel) {
        this.boatTandel = boatTandel;
    }

    public long getBoatAuctioner() {
        return boatAuctioner;
    }

    public void setBoatAuctioner(long boatAuctioner) {
        this.boatAuctioner = boatAuctioner;
    }

    public String getBoatStatus() {
        return boatStatus;
    }

    public void setBoatStatus(String boatStatus) {
        this.boatStatus = boatStatus;
    }

    public int getBlockStatus() {
        return blockStatus;
    }

    public void setBlockStatus(int blockStatus) {
        this.blockStatus = blockStatus;
    }

    public String getTandelName() {
        return tandelName;
    }

    public void setTandelName(String tandelName) {
        this.tandelName = tandelName;
    }

    public String getAuctionerName() {
        return auctionerName;
    }

    public void setAuctionerName(String auctionerName) {
        this.auctionerName = auctionerName;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
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

    public long getEnterBy() {
        return enterBy;
    }

    public void setEnterBy(long enterBy) {
        this.enterBy = enterBy;
    }

    @Override
    public String toString() {
        return "BoatDisp{" +
                "boatId=" + boatId +
                ", boatName='" + boatName + '\'' +
                ", boatOwner=" + boatOwner +
                ", regNo='" + regNo + '\'' +
                ", boatPurchaseDate=" + boatPurchaseDate +
                ", boatLenght=" + boatLenght +
                ", boatBreadth=" + boatBreadth +
                ", boatHeight=" + boatHeight +
                ", fishingType='" + fishingType + '\'' +
                ", boatLicNo='" + boatLicNo + '\'' +
                ", boatLicFromDt=" + boatLicFromDt +
                ", boatLicToDt=" + boatLicToDt +
                ", boatInsuranceFromDt=" + boatInsuranceFromDt +
                ", boatInsuranceToDt=" + boatInsuranceToDt +
                ", boatEngineType='" + boatEngineType + '\'' +
                ", boatHPRating=" + boatHPRating +
                ", boatIsUsed=" + boatIsUsed +
                ", boatTandel=" + boatTandel +
                ", boatAuctioner=" + boatAuctioner +
                ", boatStatus='" + boatStatus + '\'' +
                ", blockStatus=" + blockStatus +
                ", tandelName='" + tandelName + '\'' +
                ", auctionerName='" + auctionerName + '\'' +
                ", ownerName='" + ownerName + '\'' +
                ", coId=" + coId +
                ", enterDate=" + enterDate +
                ", enterBy=" + enterBy +
                '}';
    }
}
