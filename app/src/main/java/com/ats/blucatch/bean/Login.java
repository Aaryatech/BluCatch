package com.ats.blucatch.bean;

/**
 * Created by maxadmin on 7/9/17.
 */

public class Login {

    private User user;
    private ErrorMessage errorMessage;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

}
