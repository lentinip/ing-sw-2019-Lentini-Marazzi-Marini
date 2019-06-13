package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.network.messages.LeaderBoard;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.*;

public class LeaderBoardController {

    /* Attributes */
    private GUI gui;

    @FXML
    private Label position0;

    @FXML
    private Label position1;

    @FXML
    private Label position2;

    @FXML
    private Label position3;

    @FXML
    private Label position4;

    private List<Label> positions = new ArrayList<>();

    @FXML
    private Label username0;

    @FXML
    private Label username1;

    @FXML
    private Label username2;

    @FXML
    private Label username3;

    @FXML
    private Label username4;

    private List<Label> usernames = new ArrayList<>();

    /* Methods */

    public void initialize(){

        positions.add(position0);
        positions.add(position1);
        positions.add(position2);
        positions.add(position3);
        positions.add(position4);

        usernames.add(username0);
        usernames.add(username1);
        usernames.add(username2);
        usernames.add(username3);
        usernames.add(username4);
    }

    public void configure(GUI gui, LeaderBoard leaderBoard, List<String> usernameStrings, List<Character> characters){
        this.gui = gui;

        Map<Character, String> usernamesMap = createUsernamesMap(usernameStrings, characters);

        int i=0;
        for (Map.Entry<Character, Integer> entry : leaderBoard.getLeaderBoard().entrySet()){

            boolean first=false;
            //Set the position
            Label position = positions.get(i);

            if (entry.getValue() == 1){
                first = true;
            }

            position.setText(entry.getValue().toString()+".");

            //Builds the String to show
            Label usernameField = usernames.get(i);
            String stringToShow;

            String username = usernamesMap.get(entry.getKey());
            String points = leaderBoard.getPointsMap().get(entry.getKey()).toString();

            stringToShow = username + " - " + points + " points";

            usernameField.setText(stringToShow);


            //Shows everything

            if (first){
                position.setTextFill(Color.RED);
                usernameField.setTextFill(Color.RED);
            }

            position.setVisible(true);
            usernameField.setVisible(true);

            i++;
        }

    }

    public Map<Character, String> createUsernamesMap(List<String> usernames, List<Character> characters){
        Map<Character, String> map = new EnumMap<>(Character.class);
        for (int i=0; i<usernames.size(); i++){
            map.put(characters.get(i), usernames.get(i));
        }
        return map;
    }

    @FXML
    public void handleExitGameButton(ActionEvent actionEvent){
        Platform.exit();
        System.exit(0);
    }

    @FXML
    public void handleNewGameButton(ActionEvent actionEvent){
        Button button = (Button) actionEvent.getSource();
        Stage stage = (Stage) button.getScene().getWindow();
        stage.close();

        gui.getPrimaryStage().close();
        gui.displayLoginWindow();
    }

}
