package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 7/9/17.
 */

public class Account {

    private long accId;
    private String accType;
    private String accName;
    private String empType;
    private long tandelAccId;
    private String empMobile;
    private long empDOB;
    private String empAddress;
    private String empAadhar;
    private String empBankDetails;
    private int empPerShare;
    private String remark;
    private int isUsed;
    private String empBloodGroup;
    private String emergencyName1;
    private String emergencyMobile1;
    private String emergencyName2;
    private String emergencyMobile2;
    private double openingBalance;
    private String transactionType;
    private int coId;
    private long enterDate;
    private long enterBy;

    public Account() {
    }

    public Account(long accId, String accType, String accName, String empType, long tandelAccId, String empMobile, long empDOB, String empAddress, String empAadhar, String empBankDetails, int empPerShare, String remark, int isUsed, String empBloodGroup, String emergencyName1, String emergencyMobile1, String emergencyName2, String emergencyMobile2, double openingBalance, String transactionType, int coId, long enterDate, long enterBy) {
        this.accId = accId;
        this.accType = accType;
        this.accName = accName;
        this.empType = empType;
        this.tandelAccId = tandelAccId;
        this.empMobile = empMobile;
        this.empDOB = empDOB;
        this.empAddress = empAddress;
        this.empAadhar = empAadhar;
        this.empBankDetails = empBankDetails;
        this.empPerShare = empPerShare;
        this.remark = remark;
        this.isUsed = isUsed;
        this.empBloodGroup = empBloodGroup;
        this.emergencyName1 = emergencyName1;
        this.emergencyMobile1 = emergencyMobile1;
        this.emergencyName2 = emergencyName2;
        this.emergencyMobile2 = emergencyMobile2;
        this.openingBalance = openingBalance;
        this.transactionType = transactionType;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public Account(String accType, String accName, String empType, long tandelAccId, String empMobile, long empDOB, String empAddress, String empAadhar, String empBankDetails, int empPerShare, String remark, int isUsed, String empBloodGroup, String emergencyName1, String emergencyMobile1, String emergencyName2, String emergencyMobile2, double openingBalance, String transactionType, int coId, long enterDate, long enterBy) {
        this.accType = accType;
        this.accName = accName;
        this.empType = empType;
        this.tandelAccId = tandelAccId;
        this.empMobile = empMobile;
        this.empDOB = empDOB;
        this.empAddress = empAddress;
        this.empAadhar = empAadhar;
        this.empBankDetails = empBankDetails;
        this.empPerShare = empPerShare;
        this.remark = remark;
        this.isUsed = isUsed;
        this.empBloodGroup = empBloodGroup;
        this.emergencyName1 = emergencyName1;
        this.emergencyMobile1 = emergencyMobile1;
        this.emergencyName2 = emergencyName2;
        this.emergencyMobile2 = emergencyMobile2;
        this.openingBalance = openingBalance;
        this.transactionType = transactionType;
        this.coId = coId;
        this.enterDate = enterDate;
        this.enterBy = enterBy;
    }

    public long getAccId() {
        return accId;
    }

    public void setAccId(long accId) {
        this.accId = accId;
    }

    public String getAccType() {
        return accType;
    }

    public void setAccType(String accType) {
        this.accType = accType;
    }

    public String getAccName() {
        return accName;
    }

    public void setAccName(String accName) {
        this.accName = accName;
    }

    public String getEmpType() {
        return empType;
    }

    public void setEmpType(String empType) {
        this.empType = empType;
    }

    public long getTandelAccId() {
        return tandelAccId;
    }

    public void setTandelAccId(long tandelAccId) {
        this.tandelAccId = tandelAccId;
    }

    public String getEmpMobile() {
        return empMobile;
    }

    public void setEmpMobile(String empMobile) {
        this.empMobile = empMobile;
    }

    public long getEmpDOB() {
        return empDOB;
    }

    public void setEmpDOB(long empDOB) {
        this.empDOB = empDOB;
    }

    public String getEmpAddress() {
        return empAddress;
    }

    public void setEmpAddress(String empAddress) {
        this.empAddress = empAddress;
    }

    public String getEmpAadhar() {
        return empAadhar;
    }

    public void setEmpAadhar(String empAadhar) {
        this.empAadhar = empAadhar;
    }

    public String getEmpBankDetails() {
        return empBankDetails;
    }

    public void setEmpBankDetails(String empBankDetails) {
        this.empBankDetails = empBankDetails;
    }

    public int getEmpPerShare() {
        return empPerShare;
    }

    public void setEmpPerShare(int empPerShare) {
        this.empPerShare = empPerShare;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsUsed() {
        return isUsed;
    }

    public void setIsUsed(int isUsed) {
        this.isUsed = isUsed;
    }

    public String getEmpBloodGroup() {
        return empBloodGroup;
    }

    public void setEmpBloodGroup(String empBloodGroup) {
        this.empBloodGroup = empBloodGroup;
    }

    public String getEmergencyName1() {
        return emergencyName1;
    }

    public void setEmergencyName1(String emergencyName1) {
        this.emergencyName1 = emergencyName1;
    }

    public String getEmergencyMobile1() {
        return emergencyMobile1;
    }

    public void setEmergencyMobile1(String emergencyMobile1) {
        this.emergencyMobile1 = emergencyMobile1;
    }

    public String getEmergencyName2() {
        return emergencyName2;
    }

    public void setEmergencyName2(String emergencyName2) {
        this.emergencyName2 = emergencyName2;
    }

    public String getEmergencyMobile2() {
        return emergencyMobile2;
    }

    public void setEmergencyMobile2(String emergencyMobile2) {
        this.emergencyMobile2 = emergencyMobile2;
    }

    public double getOpeningBalance() {
        return openingBalance;
    }

    public void setOpeningBalance(double openingBalance) {
        this.openingBalance = openingBalance;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
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
        return "Account{" +
                "accId=" + accId +
                ", accType='" + accType + '\'' +
                ", accName='" + accName + '\'' +
                ", empType='" + empType + '\'' +
                ", tandelAccId=" + tandelAccId +
                ", empMobile='" + empMobile + '\'' +
                ", empDOB='" + empDOB + '\'' +
                ", empAddress='" + empAddress + '\'' +
                ", empAadhar='" + empAadhar + '\'' +
                ", empBankDetails='" + empBankDetails + '\'' +
                ", empPerShare='" + empPerShare + '\'' +
                ", remark='" + remark + '\'' +
                ", isUsed=" + isUsed +
                ", empBloodGroup='" + empBloodGroup + '\'' +
                ", emergencyName1='" + emergencyName1 + '\'' +
                ", emergencyMobile1='" + emergencyMobile1 + '\'' +
                ", emergencyName2='" + emergencyName2 + '\'' +
                ", emergencyMobile2='" + emergencyMobile2 + '\'' +
                ", openingBalance=" + openingBalance +
                ", transactionType='" + transactionType + '\'' +
                ", coId=" + coId +
                ", enterDate=" + enterDate +
                ", enterBy=" + enterBy +
                '}';
    }
}
