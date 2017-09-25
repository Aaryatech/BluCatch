package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 10/9/17.
 */

public class ExpensesData {

    private List<Expense> expenses;
    private ErrorMessage errorMessage;

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

}

