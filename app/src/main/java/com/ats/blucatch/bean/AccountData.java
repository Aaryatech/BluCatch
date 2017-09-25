package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 7/9/17.
 */

public class AccountData {

    private List<Account> account;
    private ErrorMessage errorMessage;

    public List<Account> getAccount() {
        return account;
    }

    public void setAccount(List<Account> account) {
        this.account = account;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
