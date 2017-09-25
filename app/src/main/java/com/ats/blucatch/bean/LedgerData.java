package com.ats.blucatch.bean;

import java.util.List;

/**
 * Created by maxadmin on 20/9/17.
 */

public class LedgerData {

    private List<Ledger> ledger;
    private ErrorMessage errorMessage;

    public List<Ledger> getLedger() {
        return ledger;
    }

    public void setLedger(List<Ledger> ledger) {
        this.ledger = ledger;
    }

    public ErrorMessage getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(ErrorMessage errorMessage) {
        this.errorMessage = errorMessage;
    }
}
