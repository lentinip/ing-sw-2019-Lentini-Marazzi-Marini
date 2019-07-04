package it.polimi.sw2019.network.messages;

import com.google.gson.Gson;
import it.polimi.sw2019.model.Character;
import it.polimi.sw2019.model.Colors;
import it.polimi.sw2019.model.TypeOfAction;
import it.polimi.sw2019.network.client.ClientInterface;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * @author poligenius, lentinip, Mi97ch
 * THIS CLASS IS THE KEY OF THE NETWORK,
 * IT CAN CONTAIN EVERY INFOS TO SEND FROM THE CLIENT TO THE SERVER AND FROM THE SERVER TO THE CLIENT
 * BY CHECKIN TYPE OF MESSAGE AND TYPE OF ACTION SERVER/CONTROLLER AND CLIENT KNOW EXACTLY WHAT TO DO
 * IT CONTAINS A CLASS SERIALIZED INTO A JSON STRING SO WE JUST HAVE TO DESERIALIZE IT
 * IT IS A REALLY COOL AND BRILLIANT IDEA BECAUSE IN THIS WAY WE DID NOT USE CAST AT ALL!!!!!!!!
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

    public String  serializeLoginMessage(LoginMessage loginMessage){

        Gson gson = new Gson();
        return gson.toJson(loginMessage);
    }

    public String serializeActionReport(ActionReports actionReports){

        Gson gson = new Gson();
        return gson.toJson(actionReports);
    }

    public ActionReports deserializeActionReports(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, ActionReports.class);
    }

    public MatchStart deserializeMatchStart(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, MatchStart.class);
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

    public LoginReport deserializeLoginReport(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, LoginReport.class);
    }

    public LoginMessage deserializeLoginMessage(){

        Gson gson = new Gson();
        return gson.fromJson(jsonFile, LoginMessage.class);
    }

    /* the following methods are called to create different types of Message Class */

    /**
     * creates a message containing login infos
     * @param username username of the player
     * @param rmi true if it uses rmi false if it uses socket
     */
    public void createLoginMessage(String username, boolean rmi){

        setTypeOfMessage(TypeOfMessage.LOGIN_REPORT);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializeLoginMessage(new LoginMessage(username,rmi)));
    }

    /**
     * creates a message containing a match state to update gui/cli
     * @param matchState matchstate to serialize in the message
     */
    public void createMessageMatchState(MatchState matchState){

        setTypeOfMessage(TypeOfMessage.MATCH_STATE);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializeMatchState(matchState));
    }

    /**
     * creates a message containing a private hand to update gui/cli of a specific client
     * @param privateHand private hand to serialize in the message
     */
    public void createMessagePrivateHand(PrivateHand privateHand){

        setTypeOfMessage(TypeOfMessage.PRIVATE_HAND);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializePrivateHand(privateHand));
    }

    /**
     * creates a message containing the match setup chosen by the client
     * @param matchSetup match setup infos to serialize into the message
     */
    public void createMessageMatchSetup(MatchSetup matchSetup){

        setTypeOfMessage(TypeOfMessage.MATCH_SETUP);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializeMatchSetup(matchSetup));
    }

    /**
     * creates a message containing the setups chosen by the first player
     * @param matchStart match start infos to serialize into the message
     */
    public void createMessageMatchStart(MatchStart matchStart){

        setTypeOfAction(TypeOfAction.NONE);
        setTypeOfMessage(TypeOfMessage.MATCH_START);
        setJsonFile(serializeMatchStart(matchStart));
    }

    /**
     * creates a message containing the info to tell a player if he can shoot or not
     * @param canIShoot true if he can do a shoot action, false instead
     */
    public void createMessageCanIShoot(boolean canIShoot){

        setTypeOfAction(TypeOfAction.NONE);
        setTypeOfMessage(TypeOfMessage.CAN_I_SHOOT);

        BooleanMessage booleanMessage = new BooleanMessage(canIShoot);
        setJsonFile(serializeBooleanMessage(booleanMessage));

    }

    /**
     * creates a generic ask message
     * @param typeOfAction type of message specified by who is making the ask question
     */
    public void createAskMessage(TypeOfAction typeOfAction){

        setTypeOfAction(typeOfAction);
        setTypeOfMessage(TypeOfMessage.ASK);
        setJsonFile(null);                        /* no content */
    }

    /**
     * creates a message containing infos about the cells a player can choose
     * @param typeOfAction type of action specified by who is creating the message
     * @param cells cells that the player can select
     */
    public void createAvailableCellsMessage(TypeOfAction typeOfAction, List<BoardCoord> cells){

        setTypeOfMessage(TypeOfMessage.AVAILABLE_CELLS);
        setTypeOfAction(typeOfAction);
        AvailableCells availableCells = new AvailableCells(cells);
        setJsonFile(serializeAvailableCells(availableCells));
    }

    /**
     * creates a message containing infos about the cards a player can choose
     * @param typeOfAction type of action specified by who is creating the message
     * @param indexMessageList indexes of the cards the player can select
     * @param areWeapons tells if these cards are powerups or weapons
     */
    public void createAvailableCardsMessage(TypeOfAction typeOfAction, List<IndexMessage> indexMessageList, boolean areWeapons){

        setTypeOfMessage(TypeOfMessage.AVAILABLE_CARDS);
        setTypeOfAction(typeOfAction);
        AvailableCards availableCards = new AvailableCards(indexMessageList, areWeapons);
        setJsonFile(serializeAvailableCards(availableCards));
    }

    /**
     * creates a message containing payment infos
     * @param powerups tells the powerups that can be used
     * @param mustPay tells if the player must pay the cost with a powerup or can pay woth ammo too
     */
    public void createPaymentMessage(List<IndexMessage> powerups, boolean mustPay){

        setTypeOfMessage(TypeOfMessage.PAYMENT);
        setTypeOfAction(TypeOfAction.PAY);
        PaymentMessage paymentMessage = new PaymentMessage(mustPay, powerups);
        setJsonFile(serializePaymentMessage(paymentMessage));

    }

    /**
     * creates a message containing infos about players that the client can choose
     * @param typeOfAction type of action specified by who is creating the message
     * @param players players that the player can select
     */
    public void createAvailablePlayers(TypeOfAction typeOfAction, List<Character> players){
        setTypeOfMessage(TypeOfMessage.AVAILABLE_PLAYERS);
        setTypeOfAction(typeOfAction);
        Players playersMessage = new Players(players);
        setJsonFile(serializePlayers(playersMessage));
    }

    /**
     * creates a message containing infos about the effects a player can select
     * @param availableEffects effects available
     * @param names name of the effects
     * @param weaponName name of the weapon
     */
    public void createAvailableEffects(List<IndexMessage> availableEffects, List<String> names, String weaponName){

        setTypeOfMessage(TypeOfMessage.AVAILABLE_EFFECTS);
        setTypeOfAction(TypeOfAction.SHOOT);
        AvailableEffects effects = new AvailableEffects(availableEffects, names, weaponName);
        setJsonFile(serializeAvailableEffects(effects));
    }

    /**
     * creates a message containing the infos about the leaderboard
     * @param map map containing the players in order of points
     * @param pointsMap map containing how many points every player did
     */
    public void createLeaderBoard(Map<Character, Integer> map, Map<Character, Integer> pointsMap){

        setTypeOfMessage(TypeOfMessage.END_MATCH);
        setTypeOfAction(TypeOfAction.NONE);
        LeaderBoard leaderBoard = new LeaderBoard(map, pointsMap);
        setJsonFile(serializeLeaderBoard(leaderBoard));
    }

    /**
     * creates a message containing infos about a login
     * @param connected tells if the login was succesful or not
     */
    public void createLoginReport(Boolean connected) {

        setTypeOfMessage(TypeOfMessage.LOGIN_REPORT);
        setTypeOfAction(TypeOfAction.NONE);
        LoginReport loginReport = new LoginReport(connected);
        setJsonFile(serializeLoginReport(loginReport));
    }

    /**
     * creates a message containing infos about a login
     * @param numOfPlayers number of players connected at the moment
     */
    public void createLoginReport(int numOfPlayers) {

        setTypeOfMessage(TypeOfMessage.LOGIN_REPORT);
        setTypeOfAction(TypeOfAction.NONE);
        LoginReport loginReport = new LoginReport(numOfPlayers);
        setJsonFile(serializeLoginReport(loginReport));
    }

    /**
     * creates a message containing infos about the cell selected by a player
     * @param cellSelected cell selected by the player
     * @param typeOfAction type of action specified by who is creating the message
     * @param typeOfMessage type of message specified by who is creating the message
     */
    public void createSelectedCellMessage(BoardCoord cellSelected, TypeOfAction typeOfAction, TypeOfMessage typeOfMessage){

        setTypeOfMessage(typeOfMessage);
        setTypeOfAction(typeOfAction);
        setJsonFile(serializeBoardCoord(cellSelected));
    }

    /**
     * creates a message containing infos for a grab weapon
     * @param grabWeapon GrabWeapon to serialize in the message
     */
    public void createSingleActionGrabWeapon(GrabWeapon grabWeapon){

        setTypeOfMessage(TypeOfMessage.SINGLE_ACTION);
        setTypeOfAction(TypeOfAction.GRABWEAPON);
        setJsonFile(serializeGrabWeapon(grabWeapon));
    }

    /**
     * creates a message containing infos about the reload
     * @param weaponReloaded weapon to reload
     */
    public void createSingleActionReload(int weaponReloaded){

        setTypeOfMessage(TypeOfMessage.SINGLE_ACTION);
        setTypeOfAction(TypeOfAction.RELOAD);
        setJsonFile(serializeIndexMessage(new IndexMessage(weaponReloaded)));
    }

    /**
     * creates a message containing infos about a powerup chosen
     * @param selectedPowerup powerup chosen
     */
    public void createSelectionForUsePowerup(int selectedPowerup){

        setTypeOfMessage(TypeOfMessage.SELECTED_CARD);
        setTypeOfAction(TypeOfAction.USEPOWERUP);
        setJsonFile(serializeIndexMessage(new IndexMessage(selectedPowerup)));
    }

    /**
     * creates a message containing infos about a player chosen
     * @param selectedCharacter player chosen
     * @param typeOfAction type of action specified by who is creating the message
     */
    public void createSelectedPlayer(int selectedCharacter, TypeOfAction typeOfAction){

        setTypeOfMessage(TypeOfMessage.SELECTED_PLAYER);
        setTypeOfAction(typeOfAction);
        setJsonFile(serializeIndexMessage(new IndexMessage(selectedCharacter)));
    }

    /**
     * creates a message containing infos to end a turn
     */
    public void createEndTurnMessage(){

        setTypeOfMessage(TypeOfMessage.SINGLE_ACTION);
        setTypeOfAction(TypeOfAction.ENDTURN);
        setJsonFile(null);
    }

    /**
     * creates a message containing infos about the disconnection of a player
     * @param userDisconnected player disconnected
     */
    public void createDisconnectionMessage(int userDisconnected){

        setTypeOfMessage(TypeOfMessage.DISCONNECTED);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializeIndexMessage(new IndexMessage(userDisconnected)));
    }

    /**
     * creates a message containing infos for a spawn
     * @param powerupIndex powerup selected to spawn
     */
    public void createSpawnSelection(int powerupIndex){

        setTypeOfMessage(TypeOfMessage.SELECTED_CARD);
        setTypeOfAction(TypeOfAction.SPAWN);
        setJsonFile(serializeIndexMessage(new IndexMessage(powerupIndex)));
    }

    /**
     * creates a message containing infos about a selected card
     * @param indexSelected selected card
     * @param typeOfAction type of action specified by who is creating the message
     */
    public void createSelectedCard(int indexSelected, TypeOfAction typeOfAction){

        setTypeOfMessage(TypeOfMessage.SELECTED_CARD);
        setTypeOfAction(typeOfAction);
        setJsonFile(serializeIndexMessage(new IndexMessage(indexSelected)));
    }

    /**
     * creates a message containing infos related a payment choice
     * @param indexSelected powerup selected to pay
     */
    public void createPaymentSelection(int indexSelected){
        setTypeOfMessage(TypeOfMessage.PAYMENT);
        setTypeOfAction(TypeOfAction.PAY);
        setJsonFile(serializeIndexMessage(new IndexMessage(indexSelected)));
    }

    /**
     * creates a message containing infos about the selection of a color
     * @param color color selected
     */
    public void createColorSelection(Colors color){

        setTypeOfMessage(TypeOfMessage.SELECTED_COLOR);
        setTypeOfAction(TypeOfAction.PAY);
        setJsonFile(serializeSelectedColor(new SelectedColor(color)));
    }

    /**
     * creates a message containing infos about the selection of an effect
     * @param selection effect selected
     */
    public void createSelectedEffect(int selection){

        setTypeOfMessage(TypeOfMessage.SELECTED_EFFECT);
        setTypeOfAction(TypeOfAction.SHOOT);
        setJsonFile(serializeIndexMessage(new IndexMessage(selection)));
    }

    /**
     * creates a message containing textual information about actions made by a player
     * @param report tells what a player just did
     * @param subject who made the action
     * @param receiver who received the action (ex: who has been damaged)
     */
    public void createActionReports(String report, Character subject, Character receiver){

        setTypeOfMessage(TypeOfMessage.ACTION_REPORT);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(serializeActionReport(new ActionReports(report, subject, receiver)));
    }

    /**
     * creates a message containing textual information about actions made by a player
     * @param report tells what a player just did
     * @param subject who made the action
     * @param receiver who received the action (ex: who has been damaged)
     * @param isDamageSession tells if we are in damage session
     */
    public void createActionReports(String report, Character subject, Character receiver, boolean isDamageSession){
        setTypeOfMessage(TypeOfMessage.ACTION_REPORT);
        setTypeOfAction(TypeOfAction.NONE);
        ActionReports actionReports = new ActionReports(report, subject, receiver);
        actionReports.setDamageSession(isDamageSession);
        setJsonFile(serializeActionReport(actionReports));

    }

    /**
     * creates a message to ask a player if he wants to reconnect
     */
    public void createReconnectionMessage() {

        setTypeOfMessage(TypeOfMessage.PLAYER_ALREADY_LOGGED);
        setTypeOfAction(TypeOfAction.NONE);
        setJsonFile(null);
    }
}
