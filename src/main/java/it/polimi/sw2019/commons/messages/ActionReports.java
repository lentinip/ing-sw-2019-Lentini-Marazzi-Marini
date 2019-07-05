package it.polimi.sw2019.commons.messages;

import it.polimi.sw2019.commons.Character;

/**
 * @author poligenius
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

    //Used to know in the view if we're in the damage session (Ex: Targeting Scope)
    private boolean damageSession = false;

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

    public void setReport(String report) {
        this.report = report;
    }

    public void setDamageSession(boolean damageSession) {
        this.damageSession = damageSession;
    }

    public boolean isDamageSession() {
        return damageSession;
    }
}
