package it.polimi.sw2019.network.messages;

import it.polimi.sw2019.model.Character;

import java.util.Map;

public class LeaderBoard {

    public LeaderBoard(Map<Character, Integer> map){

        setLeaderBoard(map);
    }

    /* Attributes */

    private  Map<Character, Integer> leaderBoard;

    /* methods */

    public Map<Character, Integer> getLeaderBoard() {
        return leaderBoard;
    }

    public void setLeaderBoard(Map<Character, Integer> leaderBoard) {
        this.leaderBoard = leaderBoard;
    }
}
