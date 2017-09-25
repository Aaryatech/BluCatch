package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 19/9/17.
 */

public class AccountListData {

    private List<ShowAccount> showAccount ;
    private ErrorMessage errorMessage;

    public List<ShowAccount> getShowAccount() {
        return showAccount;
    }

    public void setShowAccount(List<ShowAccount> showAccount) {
        this.showAccount = showAccount;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

}
