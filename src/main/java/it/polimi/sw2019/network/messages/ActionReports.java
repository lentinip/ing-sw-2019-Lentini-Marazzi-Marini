package it.polimi.sw2019.network.messages;

import it.polimi.sw2019.model.Character;

/**
 * class used to send reports about players actions
 */
public class ActionReports {

    public ActionReports(String report, Character subject, Character receiver){

        this.receiver = receiver;
        this.subject = subject;
        this.report = report;
    }

    /* Attributes */

    private String report;

    private Character subject;

    private Character receiver;

    /* Methods */

    public Character getReceiver() {
        return receiver;
    }

    public Character getSubject() {
        return subject;
    }

    public String getReport() {
        return report;
    }

    public void setReceiver(Character receiver) {
        this.receiver = receiver;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public void setSubject(Character subject) {
        this.subject = subject;
    }
}
