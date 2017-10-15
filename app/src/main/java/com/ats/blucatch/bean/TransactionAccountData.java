package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 21/9/17.
 */

public class TransactionAccountData {

    private List<SenderAccount> senderAccount;
    private List<ReceiverAccount> receiverAccount;
    private double sellCount;
    private ErrorMessage errorMessage;

    public List<SenderAccount> getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(List<SenderAccount> senderAccount) {
        this.senderAccount = senderAccount;
    }

    public List<ReceiverAccount> getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(List<ReceiverAccount> receiverAccount) {
        this.receiverAccount = receiverAccount;
    }

    public double getSellCount() {
        return sellCount;
    }

    public void setSellCount(double sellCount) {
        this.sellCount = sellCount;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString() {
        return "TransactionAccountData{" +
                "senderAccount=" + senderAccount +
                ", receiverAccount=" + receiverAccount +
                ", sellCount=" + sellCount +
                ", errorMessage=" + errorMessage +
                '}';
    }
}
