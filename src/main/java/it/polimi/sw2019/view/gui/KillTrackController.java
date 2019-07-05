package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.commons.Character;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Controller for the KillTrackScreen
 *
 * @author lentinip
 */
public class KillTrackController {

    /* Attributes */

    @FXML
    private Label bansheeLabel;

    @FXML
    private Label distructorLabel;

    @FXML
    private Label dozerLabel;

    @FXML
    private Label sprogLabel;

    @FXML
    private Label violetLabel;

    private Map<Character, Label> labelsMap = new EnumMap<>(Character.class);

    private Map<Character, Integer> integerMap = new EnumMap<>(Character.class);

    /* Methods */

    /**
     * Initializes the Maps
     */
    public void initialize(){

        labelsMap.put(Character.BANSHEE, bansheeLabel);
        labelsMap.put(Character.DISTRUCTOR, distructorLabel);
        labelsMap.put(Character.DOZER, dozerLabel);
        labelsMap.put(Character.SPROG, sprogLabel);
        labelsMap.put(Character.VIOLET, violetLabel);

        integerMap.put(Character.BANSHEE, 0);
        integerMap.put(Character.DISTRUCTOR, 0);
        integerMap.put(Character.DOZER, 0);
        integerMap.put(Character.SPROG, 0);
        integerMap.put(Character.VIOLET, 0);
    }

    /**
     * Updates the KillTrackScreen with the new data
     * @param killTrack List of characters that killed somebody
     * @param overKillTokens List of boolean of the overkills (true if it is an overkill)
     */
    public void updateTokens(List<Character> killTrack, List<Boolean> overKillTokens){
        for (int i=0; i<killTrack.size(); i++){
            Character character = killTrack.get(i);
            Integer newValue = integerMap.get(character);
            newValue++;
            if (overKillTokens.get(i)){
                newValue++;
            }
            integerMap.replace(character, newValue);

            showValues();
        }
    }

    /**
     * Sets the number of the tokens on the killTrack for each player in his label on the KillTrackScreen
     */
    public void showValues(){
        for (Map.Entry<Character, Label> labelEntry : labelsMap.entrySet()){
            Integer value = integerMap.get(labelEntry.getKey());
            labelEntry.getValue().setText(value.toString());
        }
    }
}
