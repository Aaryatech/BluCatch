package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 26/9/17.
 */

public class Season {

    private int seasonId;
    private long startDate;
    private long endDate;
    private String remark;
    private int isActive;
    private int coId;
    private long enterDate;
    private long enterBy;

    public Season(int seasonId, long startDate, long endDate, String remark, int isActive, int coId, long enterDate, long enterBy) {
        this.seasonId = seasonId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.remark = remark;
        this.isActive = isActive;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public Season(long startDate, long endDate, String remark, int isActive, int coId, long enterDate, long enterBy) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.remark = remark;
        this.isActive = isActive;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public int getSeasonId() {
        return seasonId;
    }

    public void setSeasonId(int seasonId) {
        this.seasonId = seasonId;
    }

    public long getStartDate() {
        return startDate;
    }

    public void setStartDate(long startDate) {
        this.startDate = startDate;
    }

    public long getEndDate() {
        return endDate;
    }

    public void setEndDate(long endDate) {
        this.endDate = endDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsActive() {
        return isActive;
    }

    public void setIsActive(int isActive) {
        this.isActive = isActive;
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
        return "Season{" +
                "seasonId=" + seasonId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", remark='" + remark + '\'' +
                ", isActive=" + isActive +
                ", coId=" + coId +
                ", enterDate=" + enterDate +
                ", enterBy=" + enterBy +
                '}';
    }
}
