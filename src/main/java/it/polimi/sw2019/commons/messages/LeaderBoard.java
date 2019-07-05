package it.polimi.sw2019.commons.messages;

import it.polimi.sw2019.commons.Character;

import java.util.Map;

/**
 * @author poligenius
 * this class contains infos about the leaderboard when the match ends
 */
public class LeaderBoard {

    public LeaderBoard(Map<Character, Integer> map, Map<Character, Integer> pointsMap){

        setLeaderBoard(map);
        setPointsMap(pointsMap);
    }

    /* Attributes */

    private  Map<Character, Integer> ranking;

    private Map<Character, Integer> pointsMap;

    /* methods */

    public Map<Character, Integer> getPointsMap() {
        return pointsMap;
    }

    public void setPointsMap(Map<Character, Integer> pointsMap) {
        this.pointsMap = pointsMap;
    }

    public Map<Character, Integer> getLeaderBoard() {
        return ranking;
    }

    public void setLeaderBoard(Map<Character, Integer> leaderBoard) {
        this.ranking = leaderBoard;
    }
}
