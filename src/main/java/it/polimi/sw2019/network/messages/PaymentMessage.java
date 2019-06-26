package it.polimi.sw2019.network.messages;

import java.util.ArrayList;
import java.util.List;

public class PaymentMessage {

    public PaymentMessage(boolean mustPay, List<IndexMessage> usablePowerups){
        setMustPay(mustPay);
        setUsablePowerups(usablePowerups);
    }


    /* Attributes */

    private boolean mustPay;

    private List<IndexMessage> usablePowerups = new ArrayList<>();

    /* Methods */

    public boolean isMustPay() {
        return mustPay;
    }

    public void setMustPay(boolean mustPay) {
        this.mustPay = mustPay;
    }

    public List<IndexMessage> getUsablePowerups() {
        return usablePowerups;
    }

    public void setUsablePowerups(List<IndexMessage> usablePowerups) {
        this.usablePowerups = usablePowerups;
    }
}
