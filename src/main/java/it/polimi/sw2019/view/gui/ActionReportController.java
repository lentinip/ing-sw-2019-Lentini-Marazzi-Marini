package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.network.messages.ActionReports;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;

public class ActionReportController {
    @FXML
    TextArea textArea;

    public void initialize(){
        textArea.setStyle("text-area-background: black;");
        textArea.setStyle("-fx-background-color: black;");
        textArea.setStyle("-fx-text-inner-color: white;");
    }

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

    public void clear(){
        textArea.setText("");
    }
}
