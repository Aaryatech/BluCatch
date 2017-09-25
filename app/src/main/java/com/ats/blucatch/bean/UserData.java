package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 8/9/17.
 */

public class UserData {

    private List<User> user;
    private ErrorMessage errorMessage;

    public List<User> getUser() {
        return user;
    }

    public void setUser(List<User> user) {
        this.user = user;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
