package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 20/9/17.
 */

public class ExpenseApproveData {

    private List<ExpenseApprove> expenseApprove;
    private ErrorMessage errorMessage;

    public List<ExpenseApprove> getExpenseApprove() {
        return expenseApprove;
    }

    public void setExpenseApprove(List<ExpenseApprove> expenseApprove) {
        this.expenseApprove = expenseApprove;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }


    @Override
    public String toString() {
        return "ExpenseApproveData{" +
                "expenseApprove=" + expenseApprove +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
