package com.ats.blucatch.bean;

public class HomeCountData {

    private double sellCount;
    private double expenseCount;
    private double incomeCount;

    public double getSellCount() {
        return sellCount;
    }

    public void setSellCount(double sellCount) {
        this.sellCount = sellCount;
    }

    public double getExpenseCount() {
        return expenseCount;
    }

    public void setExpenseCount(double expenseCount) {
        this.expenseCount = expenseCount;
    }

    public double getIncomeCount() {
        return incomeCount;
    }

    public void setIncomeCount(double incomeCount) {
        this.incomeCount = incomeCount;
    }

    @Override
    public String toString() {
        return "HomeCountData{" +
                "sellCount=" + sellCount +
                ", expenseCount=" + expenseCount +
                ", incomeCount=" + incomeCount +
                '}';
    }
}
