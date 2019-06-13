package it.polimi.sw2019.view.gui;

import it.polimi.sw2019.model.Character;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

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

    private Integer banshee = 0;

    private Integer distructor = 0;

    private Integer dozer = 0;

    private Integer sprog = 0;

    private Integer violet = 0;

    private Map<Character, Integer> integerMap = new EnumMap<>(Character.class);

    /* Methods */

    public void initialize(){

        labelsMap.put(Character.BANSHEE, bansheeLabel);
        labelsMap.put(Character.DISTRUCTOR, distructorLabel);
        labelsMap.put(Character.DOZER, dozerLabel);
        labelsMap.put(Character.SPROG, sprogLabel);
        labelsMap.put(Character.VIOLET, violetLabel);

        integerMap.put(Character.BANSHEE, banshee);
        integerMap.put(Character.DISTRUCTOR, distructor);
        integerMap.put(Character.DOZER, dozer);
        integerMap.put(Character.SPROG, sprog);
        integerMap.put(Character.VIOLET, violet);
    }

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

    public void showValues(){
        for (Map.Entry<Character, Label> labelEntry : labelsMap.entrySet()){
            Integer value = integerMap.get(labelEntry.getKey());
            labelEntry.getValue().setText(value.toString());
        }
    }
}
