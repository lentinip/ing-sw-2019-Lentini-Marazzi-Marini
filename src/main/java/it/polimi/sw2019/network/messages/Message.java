package it.polimi.sw2019.network.messages;

import com.google.gson.Gson;
import it.polimi.sw2019.model.Board;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.TypeOfAction;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * generic class used from view and controller to send information
 */
public class Message implements Serializable {

    /**
     * Default Constructor
     */
    public Message(){}

    /**
     * customize constructor
     * @param username name of the receiver
     */
    public Message(String username){
        this.username = username;
    }

    /* Attributes */

    private String username;  /* if null the message is for all players */

    private TypeOfMessage typeOfMessage;

    private TypeOfAction typeOfAction;

    private String jsonFile; /* the information used by the controller and the view are serialized in this string */

    /* Methods */

    public TypeOfAction getTypeOfAction() {
        return typeOfAction;
    }

    public void setTypeOfAction(TypeOfAction typeOfAction) {
        this.typeOfAction = typeOfAction;
    }

    public String getJsonFile() {
        return jsonFile;
    }

    public void setJsonFile(String jsonFile) {
        this.jsonFile = jsonFile;
    }

    public String getUsername() {
        return username;
    }

    public void setTypeOfMessage(TypeOfMessage typeOfMessage) {
        this.typeOfMessage = typeOfMessage;
    }

    public TypeOfMessage getTypeOfMessage() {
        return typeOfMessage;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /* The following methods are written to serialize every message class and create the attribute jsonFile */

    public String serializeMatchStart(MatchStart matchStart){

        Gson gson = new Gson();
        return gson.toJson(matchStart);
    }

    public String serializeMatchSetup(MatchSetup matchSetup){

        Gson gson = new Gson();
        return gson.toJson(matchSetup);
    }

    public String serializeAvailableEffects(AvailableEffects availableEffects){

        Gson gson = new Gson();
        return gson.toJson(availableEffects);
    }

    public String serializeGrabWeapon(GrabWeapon grabWeapon){

        Gson gson = new Gson();
        return gson.toJson(grabWeapon);
    }

    public String serializeIndexMessage(IndexMessage indexMessage){

        Gson gson = new Gson();
        return gson.toJson(indexMessage);
    }

    public String serializePlayers(Players players){

        Gson gson = new Gson();
        return gson.toJson(players);
    }

    public String serializeMatchState(MatchState matchState){

        Gson gson = new Gson();
        return gson.toJson(matchState);
    }

    public String serializePrivateHand(PrivateHand privateHand){

        Gson gson = new Gson();
        return gson.toJson(privateHand);
    }

    public String serializeBooleanMessage(BooleanMessage booleanMessage){

        Gson gson = new Gson();
        return gson.toJson(booleanMessage);
    }

    public String serializeAvailableCards(AvailableCards availableCards){

        Gson gson = new Gson();
        return gson.toJson(availableCards);
    }

    public String serializeAvailableCells(AvailableCells availableCells){

        Gson gson = new Gson();
        return gson.toJson(availableCells);
    }

    public String serializeBoardCoord(BoardCoord boardCoord){

        Gson gson = new Gson();
        return gson.toJson(boardCoord);
    }

    public String serializePaymentMessage(PaymentMessage paymentMessage){

        Gson gson = new Gson();
        return gson.toJson(paymentMessage);
    }

    public String serializeSelectedColor(SelectedColor selectedColor){

        Gson gson = new Gson();
        return gson.toJson(selectedColor);
    }

    public String serializeLeaderBoard(LeaderBoard leaderBoard){

        Gson gson = new Gson();
        return gson.toJson(leaderBoard);
    }

    public String serializeLoginReport(LoginReport loginReport) {

        Gson gson = new Gson();
        return gson.toJson(loginReport);
    }

    public BoardCoord deserializeBoardCoord(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, BoardCoord.class);
    }

    public GrabWeapon deserializeGrabWeapon(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, GrabWeapon.class);
    }

    public IndexMessage deserializeIndexMessage(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, IndexMessage.class);
    }

    public SelectedColor deserializeSelectedColor(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, SelectedColor.class);
    }

    public Players deserializePlayersMessage(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, Players.class);
    }

    public MatchSetup deserializeMatchSetup(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, MatchSetup.class);
    }

    public LeaderBoard deserializeLeaderBoard(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, LeaderBoard.class);
    }

    public BooleanMessage deserializeBooleanMessage(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, BooleanMessage.class);
    }

    public AvailableCells deserializeAvailableCells(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, AvailableCells.class);
    }

    public PaymentMessage deserializePaymentMessage(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, PaymentMessage.class);
    }

    public MatchState deserializeMatchState(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, MatchState.class);
    }

    public PrivateHand deserializePrivateHand(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, PrivateHand.class);
    }

    public AvailableCards deserializeAvailableCards(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, AvailableCards.class);
    }

    public AvailableEffects deserializeAvailableEffects(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, AvailableEffects.class);
    }

    /* the following methods are called to create different types of Message Class */

    public void createMessageMatchState(MatchState matchState){

        setTypeOfMessage(TypeOfMessage.MATCH_STATE);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializeMatchState(matchState));
    }

    public void createMessagePrivateHand(PrivateHand privateHand){

        setTypeOfMessage(TypeOfMessage.PRIVATE_HAND);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializePrivateHand(privateHand));
    }

    public void createMessageMatchSetup(MatchSetup matchSetup){

        setTypeOfMessage(TypeOfMessage.MATCH_SETUP);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializeMatchSetup(matchSetup));
    }

    public void createMessageMatchStart(MatchStart matchStart){

        setTypeOfAction(TypeOfAction.NONE);
        setTypeOfMessage(TypeOfMessage.MATCH_START);
        setJsonFile(serializeMatchStart(matchStart));
    }

    public void createMessageCanIShoot(boolean canIShoot){

        setTypeOfAction(TypeOfAction.NONE);
        setTypeOfMessage(TypeOfMessage.CAN_I_SHOOT);

        BooleanMessage booleanMessage = new BooleanMessage(canIShoot);
        setJsonFile(serializeBooleanMessage(booleanMessage));

    }

    public void createAskMessage(TypeOfAction typeOfAction){

        setTypeOfAction(typeOfAction);
        setTypeOfMessage(TypeOfMessage.ASK);
        setJsonFile(null);                        /* no content */
    }

    public void createAvailableCellsMessage(TypeOfAction typeOfAction, List<BoardCoord> cells){

        setTypeOfMessage(TypeOfMessage.AVAILABLE_CELLS);
        setTypeOfAction(typeOfAction);
        AvailableCells availableCells = new AvailableCells(cells);
        setJsonFile(serializeAvailableCells(availableCells));
    }

    public void createAvailableCardsMessage(TypeOfAction typeOfAction, List<IndexMessage> indexMessageList, boolean areWeapons){

        setTypeOfMessage(TypeOfMessage.AVAILABLE_CARDS);
        setTypeOfAction(typeOfAction);
        AvailableCards availableCards = new AvailableCards(indexMessageList, areWeapons);
        setJsonFile(serializeAvailableCards(availableCards));
    }

    public void createPaymentMessage(List<IndexMessage> powerups, boolean mustPay){

        setTypeOfMessage(TypeOfMessage.PAYMENT);
        setTypeOfAction(TypeOfAction.PAY);
        PaymentMessage paymentMessage = new PaymentMessage(mustPay, powerups);
        setJsonFile(serializePaymentMessage(paymentMessage));

    }

    public void createAvailablePlayers(TypeOfAction typeOfAction, List<Character> players){
        setTypeOfMessage(TypeOfMessage.AVAILABLE_PLAYERS);
        setTypeOfAction(typeOfAction);
        Players playersMessage = new Players(players);
        setJsonFile(serializePlayers(playersMessage));
    }

    public void createAvailableEffects(List<IndexMessage> availableEffects){

        setTypeOfMessage(TypeOfMessage.AVAILABLE_EFFECTS);
        setTypeOfAction(TypeOfAction.SHOOT);
        AvailableEffects effects = new AvailableEffects(availableEffects);
        setJsonFile(serializeAvailableEffects(effects));
    }

    public void createLeaderBoard(Map<Character, Integer> map){

        setTypeOfMessage(TypeOfMessage.END_MATCH);
        setTypeOfAction(TypeOfAction.NONE);
        LeaderBoard leaderBoard = new LeaderBoard(map);
        setJsonFile(serializeLeaderBoard(leaderBoard));
    }

    public void createLoginReport(Boolean connected) {

        setTypeOfMessage(TypeOfMessage.LOGIN_REPORT);
        setTypeOfAction(TypeOfAction.NONE);
        LoginReport loginReport = new LoginReport(connected);
        setJsonFile(serializeLoginReport(loginReport));
    }
}
