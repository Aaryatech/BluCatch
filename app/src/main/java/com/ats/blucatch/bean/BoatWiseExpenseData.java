package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 28/9/17.
 */

public class BoatWiseExpenseData {

    private List<BoatWiseExpense> boatWiseExpenses;
    private Integer totalExpense;
    private ErrorMessage errorMessage;

    public List<BoatWiseExpense> getBoatWiseExpenses() {
        return boatWiseExpenses;
    }

    public void setBoatWiseExpenses(List<BoatWiseExpense> boatWiseExpenses) {
        this.boatWiseExpenses = boatWiseExpenses;
    }

    public Integer getTotalExpense() {
        return totalExpense;
    }

    public void setTotalExpense(Integer totalExpense) {
        this.totalExpense = totalExpense;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "BoatWiseExpenseData{" +
                "boatWiseExpenses=" + boatWiseExpenses +
                ", totalExpense=" + totalExpense +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
