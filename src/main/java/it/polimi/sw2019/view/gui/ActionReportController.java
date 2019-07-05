package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.commons.messages.ActionReports;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

/**
 * Controller for the ActionReportScreen
 *
 * @author lentinip
 */
public class ActionReportController {
    @FXML
    TextArea textArea;

    /**
     * Adds the action report content to the ActionReportScreen
     * @param actionReports actionReports received
     */
    public void manageActionReport(ActionReports actionReports){
        String stringToShow ="\n"+ actionReports.getSubject() + " " + actionReports.getReport();

        if (actionReports.getReceiver() != null){
            stringToShow = stringToShow + " " + actionReports.getReceiver() + "\n";
        }
        else {
            stringToShow = stringToShow + "\n";
        }

        textArea.appendText(stringToShow);
    }

    /**
     * Clears the text area
     */
    public void clear(){
        textArea.setText("");
    }
}
